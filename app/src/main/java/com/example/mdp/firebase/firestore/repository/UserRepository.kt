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
        return try {
            val doc = usersCollection.document(uid).get().await()
            doc.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to fetch user: ${e.message}")
            null
        }
    }

    suspend fun updateUser(user: User) {
        try {
            usersCollection.document(user.uid).set(user).await()
            Log.d("UserRepository", "User updated: ${user.uid}")
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to update user: ${e.message}")
        }
    }

    suspend fun createUserIfNotExists(user: User) {

        try {
            val docRef = usersCollection.document(user.uid)
            val snapshot = docRef.get().await()

            if (!snapshot.exists()) {
                val defaultUser = user.copy(
                    birthday = profileTimeFormatter(LocalDate.now()),
                    height = if (user.height == 0f) 200f else user.height,
                    weight = if (user.weight == 0f) 70f else user.weight,
                    age = if (user.age == 0) 18 else user.age
                )
                docRef.set(defaultUser).await()
                Log.d("UserRepository", "Created new user: ${defaultUser.uid}")
            } else {
                Log.d("UserRepository", "User already exists: ${user.uid}")
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to create user: ${e.message}")
        }
    }

}
