package com.edufun.quizzify.quizFunctions

import com.edufun.quizzify.Question

data class Quiz(
    val name: String,          // Quiz name
    val coverImageResId: Int,  // Resource ID for the cover image
    val questions: List<Question> // List of questions
)
