package com.example.mdp.firebase.auth.repository

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.mdp.R
import com.example.mdp.firebase.firestore.model.User
import com.example.mdp.firebase.firestore.repository.UserRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.SecureRandom

class AuthRepository(
    private val auth: FirebaseAuth,
    private val context: Context,
    private val userRepository: UserRepository
) {
    private val credentialManager = CredentialManager.create(context)

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun register(email: String, password: String, onResult: (FirebaseUser?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(if (task.isSuccessful) auth.currentUser else null)
            }
    }

    fun login(email: String, password: String, onResult: (FirebaseUser?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(if (task.isSuccessful) auth.currentUser else null)
            }
    }

    suspend fun signInWithGoogle(): Boolean {
        return try {
            val result = credentialManager.getCredential(context, buildGoogleSignInRequest())
            result.credential.let { credential ->
                if (credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val idToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    val authResult = auth.signInWithCredential(firebaseCredential).await()

                    authResult.user?.let { createDefaultUserIfNeeded(it) }
                    return true
                }
            }
            Log.e("AuthRepository", "Invalid credential type")
            false
        } catch (e: GetCredentialException) {
            Log.e("AuthRepository", "Google Sign-In failed: ${e.message}")
            false
        }
    }

    fun logout() {
        auth.signOut()
        Log.d("currentUser", "${auth.currentUser} from AuthRepository logout")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = androidx.credentials.ClearCredentialStateRequest()
                credentialManager.clearCredentialState(request)
                Log.d("AuthRepository", "Google credentials cleared successfully")
            } catch (e: Exception) {
                Log.e("AuthRepository", "Failed to clear Google credentials: ${e.message}")
            }
        }
    }

    //    attach and remove listener to the authentication process
    fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        auth.addAuthStateListener(listener)
    }

    fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        auth.removeAuthStateListener(listener)
    }

    //  create a google sign-in request
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
