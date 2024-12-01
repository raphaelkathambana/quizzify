package com.edufun.quizzify.quizFunctions

import android.content.Context
import android.util.Log
import com.edufun.quizzify.Question
import com.edufun.quizzify.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class FirebaseRepository {

    private val firestore = FirebaseFirestore.getInstance()

    init {
        firestore.clearPersistence() // Clear cache when the repository initializes
    }

    private val imageNameToResIdMap = mapOf(
        // "general_knowledge" to R.drawable.g,
        "math_quiz" to R.drawable.m,
        // "science_quiz" to R.drawable.s
    )

    /**
     * Fetches all quizzes from Firestore.
     */
    suspend fun fetchAllQuizzes(): List<Quiz> {
        val quizzes = mutableListOf<Quiz>()

        try {
            val result = firestore.collection("quizzes")
                .get(Source.SERVER) // Force fetching from the server
                .await()
            for (document in result.documents) {
                val name = document["Name"] as? String ?: "Unknown Quiz"
                val coverImageName = document["coverImageResId"] as? String
                val coverImageResId = imageNameToResIdMap[coverImageName] ?: R.drawable.logo

                // Parse questions directly as a list
                val questionsList = document["questions"] as? List<Map<String, Any>> ?: emptyList()
                val questions = questionsList.map { questionMap ->
                    Question(
                        questionText = questionMap["questionText"] as? String ?: "No Question",
                        options = (questionMap["options"] as? List<*>)?.mapNotNull {
                            it.toString() // Convert options to strings
                        } ?: emptyList(),
                        correctAnswerIndex = (questionMap["correctAnswerIndex"] as? Long)?.toInt() ?: -1
                    )
                }

                quizzes.add(
                    Quiz(
                        name = name,
                        coverImageResId = coverImageResId,
                        questions = questions
                    )
                )
            }
            Log.d("FirebaseRepository", "Fetched quizzes: $quizzes")
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching quizzes", e)
        }

        return quizzes
    }
}
