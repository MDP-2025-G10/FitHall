package com.example.mdp.firebase.firestore.repository

import android.util.Log
import com.example.mdp.firebase.firestore.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class WorkoutRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun userWorkoutsCollection() =
        db.collection("users").document(auth.currentUser?.uid ?: "default").collection("workouts")

    suspend fun insertWorkout(workout: Workout) {
        try {
            val workoutRef = userWorkoutsCollection().document()
            workoutRef.set(workout.copy(id = workoutRef.id)).await()
        } catch (e: Exception) {
            Log.e("workoutRepository", "Error inserting workout to firestore", e)
        }
    }

    fun getAllWorkouts(): Flow<List<Workout>> = callbackFlow {
        val listener = userWorkoutsCollection().addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }
            val workouts =
                snapshot?.documents?.mapNotNull { it.toObject(Workout::class.java) } ?: emptyList()
            trySend(workouts).isSuccess
        }
        awaitClose { listener.remove() }
    }

    fun deleteWorkout(workoutId: String) {
        val userId = auth.currentUser?.uid ?: run {
            Log.e("WorkoutRepository", "No logged-in user for deleting workout")
            return
        }

        val workoutRef = db.collection("users")
            .document(userId)
            .collection("workouts")
            .document(workoutId)

        workoutRef.delete()
            .addOnSuccessListener {
                Log.d("WorkoutRepository", "Workout deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("WorkoutRepository", "Error deleting workout", e)
            }
    }
}