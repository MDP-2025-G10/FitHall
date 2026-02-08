package com.example.mdp.firebase.firestore.repository

import android.util.Log
import com.example.mdp.firebase.firestore.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId


class WorkoutRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun userWorkoutsCollection() =
        db.collection("users")
            .document(auth.currentUser?.uid ?: "default")
            .collection("workouts")

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

    fun getWorkoutsForDate(date: LocalDate): Flow<List<Workout>> = callbackFlow {
        val startOfDay = date.atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond() * 1000
        val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond() * 1000

        val listener = userWorkoutsCollection()
            .whereGreaterThan("timestamp", startOfDay)
            .whereLessThan("timestamp", endOfDay)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val workouts = snapshot?.documents?.mapNotNull { it.toObject(Workout::class.java) }
                    ?: emptyList()
                trySend(workouts).isSuccess
            }
        awaitClose { listener.remove() }
    }

    suspend fun deleteWorkout(workout: Workout) {
        try {
            userWorkoutsCollection().document(workout.id).delete().await()
        } catch (e: Exception) {
            Log.e("WorkoutRepository", "Error deleting workout from Firestore", e)
        }
    }
}