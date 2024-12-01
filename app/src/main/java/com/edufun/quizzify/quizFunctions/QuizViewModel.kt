package com.edufun.quizzify.quizFunctions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.edufun.quizzify.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FirebaseRepository()
    val statsRepository = UserStatsRepository()

    private val _quizzes = MutableStateFlow<List<Quiz>>(emptyList())
    val quizzes: StateFlow<List<Quiz>> = _quizzes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _currentQuiz = MutableStateFlow<Quiz?>(null)
    val currentQuiz: StateFlow<Quiz?> = _currentQuiz

    private var currentIndex = 0
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    init {
        loadQuizzes()
    }

    fun loadQuizzes() {
        viewModelScope.launch {
            _loading.value = true
            val fetchedQuizzes = repository.fetchAllQuizzes()
            _quizzes.value = fetchedQuizzes
            _loading.value = false
        }
    }

    fun selectQuiz(quizName: String) {
        val quiz = _quizzes.value.find { it.name == quizName }
        _currentQuiz.value = quiz
        currentIndex = 0
        _score.value = 0
        _currentQuestion.value = quiz?.questions?.getOrNull(currentIndex)
    }

    fun submitAnswer(selectedIndex: Int) {
        if (_currentQuestion.value?.correctAnswerIndex == selectedIndex) {
            _score.value += 1
        }
        currentIndex += 1
        _currentQuestion.value = _currentQuiz.value?.questions?.getOrNull(currentIndex)
    }
    fun saveQuizResult() {
        val quizName = _currentQuiz.value?.name ?: return
        val finalScore = score.value

        viewModelScope.launch {
            statsRepository.updateQuizResult(quizName, finalScore)
        }
    }
}

