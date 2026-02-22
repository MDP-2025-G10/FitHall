package com.example.mdp.firebase.auth.repository

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.mdp.R
import com.example.mdp.firebase.firestore.model.User
import com.example.mdp.firebase.firestore.repository.UserRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.security.SecureRandom

class AuthRepository(
    private val auth: FirebaseAuth,
    private val context: Context,
    private val userRepository: UserRepository
) {

    private val credentialManager = CredentialManager.create(context)

    // fun getCurrentUser(): FirebaseUser? = auth.currentUser

    val authStateFlow: Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }

        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    suspend fun register(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login failed", e)
            Result.failure(e)
        }
    }

    suspend fun signInWithGoogle(): Result<FirebaseUser> {
        return try {
            val result = credentialManager.getCredential(
                context,
                buildGoogleSignInRequest()
            )

            val credential = result.credential

            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val idToken =
                    GoogleIdTokenCredential.createFrom(credential.data).idToken

                val firebaseCredential =
                    GoogleAuthProvider.getCredential(idToken, null)

                val authResult =
                    auth.signInWithCredential(firebaseCredential).await()

                val user = authResult.user!!

                createDefaultUserIfNeeded(user)

                Result.success(user)
            } else {
                Result.failure(Exception("Invalid credential type"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        auth.signOut()
        credentialManager.clearCredentialState(
            androidx.credentials.ClearCredentialStateRequest()
        )
    }

    private fun buildGoogleSignInRequest(): GetCredentialRequest {
        val clientId = context.getString(R.string.web_client_id)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(clientId)
            .setAutoSelectEnabled(true)
            .setNonce(generateNonce())
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private suspend fun createDefaultUserIfNeeded(firebaseUser: FirebaseUser) {
        val user = User(
            uid = firebaseUser.uid,
            name = firebaseUser.displayName.orEmpty(),
            email = firebaseUser.email.orEmpty(),
            profilePic = firebaseUser.photoUrl?.toString().orEmpty(),
            birthday = "",
            height = 0f,
            weight = 0f,
            age = 0
        )
        userRepository.createUserIfNotExists(user)
    }

    private fun generateNonce(): String {
        val nonce = ByteArray(16)
        SecureRandom().nextBytes(nonce)
        return Base64.encodeToString(nonce, Base64.NO_WRAP)
    }
}