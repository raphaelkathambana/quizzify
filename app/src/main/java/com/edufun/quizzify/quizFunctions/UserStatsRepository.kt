package com.edufun.quizzify.quizFunctions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserStatsRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun getUserDocument() =
        firestore.collection("users").document(auth.currentUser?.uid ?: "default_user")

    suspend fun updateQuizResult(quizName: String, score: Int) {
        val userDoc = getUserDocument()

        try {
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userDoc)

                // Get current quizzesDone list
                val quizzesDone = snapshot.get("quizzesDone") as? List<Map<String, Any>> ?: emptyList()
                val existingQuiz = quizzesDone.find { it["quizName"] == quizName }

                // Prepare updated quizzesDone list
                val updatedQuizzes = if (existingQuiz != null) {
                    quizzesDone.map {
                        if (it["quizName"] == quizName) {
                            mapOf("quizName" to quizName, "lastScore" to score)
                        } else {
                            it
                        }
                    }
                } else {
                    quizzesDone + mapOf("quizName" to quizName, "lastScore" to score)
                }

                // Update the total quizzes count if this is a new quiz
                val totalQuizzesDone = if (existingQuiz == null) {
                    (snapshot.getLong("totalQuizzesDone") ?: 0) + 1
                } else {
                    snapshot.getLong("totalQuizzesDone") ?: 0
                }

                // Commit the transaction
                transaction.set(
                    userDoc,
                    mapOf(
                        "quizzesDone" to updatedQuizzes,
                        "totalQuizzesDone" to totalQuizzesDone
                    )
                )
            }
        } catch (e: Exception) {
            println("Error updating quiz result: ${e.message}")
        }
    }

    suspend fun getUserStats(): Map<String, Any?> {
        return try {
            getUserDocument().get().await().data ?: emptyMap()
        } catch (e: Exception) {
            println("Error fetching user stats: ${e.message}")
            emptyMap()
        }
    }
}
