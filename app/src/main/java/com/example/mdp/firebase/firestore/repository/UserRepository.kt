package com.example.mdp.firebase.firestore.repository

import android.util.Log
import com.example.mdp.firebase.firestore.model.User
import com.example.mdp.utils.profileTimeFormatter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class UserRepository(private val db: FirebaseFirestore) {

    private val usersCollection = db.collection("users")

    suspend fun getUser(uid: String): User? {
        Log.d("UserRepository", "Fetching user with uid: $uid")
        val doc = usersCollection.document(uid).get().await()
        return doc.toObject(User::class.java)
    }

    suspend fun updateUser(user: User) {
        usersCollection.document(user.uid).set(user).await()
    }

    suspend fun createUserIfNotExists(firebaseUser: User) {
        val uid = firebaseUser.uid
        val docRef = usersCollection.document(uid)
        val snapshot = docRef.get().await()
        val today = profileTimeFormatter(LocalDate.now())
        if (!snapshot.exists()) {
            val newUser = User(
                uid = uid,
                name = firebaseUser.name,
                email = firebaseUser.email,
                profilePic = firebaseUser.profilePic,
                birthday = today,
                height = 200.0f,
                weight = 70.0f
            )
            docRef.set(newUser).await()
            Log.d("UserRepository", "User document created for $uid: $newUser")
        } else {
            Log.d("UserRepository", "User document already exists for $uid")
        }

    }

}
