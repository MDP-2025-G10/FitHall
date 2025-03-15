package com.example.mdp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepository(private val auth: FirebaseAuth) {
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun register(email: String, password: String, onResult: (FirebaseUser?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(auth.currentUser)
                } else {
                    onResult(null)
                }
            }
    }

    fun login(email: String, password: String, onResult: (FirebaseUser?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(auth.currentUser)
                } else {
                    onResult(null)
                }
            }
    }

    fun firebaseAuthWithGoogle(idToken: String, onResult: (FirebaseUser?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(auth.currentUser)
                } else {
                    onResult(null)
                }
            }
    }

    fun logout() {
        auth.signOut()
    }
}
