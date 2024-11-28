package com.edufun.quizzify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.edufun.quizzify.ui.theme.Orange

// List of Questions
data class ListDetail(
    val pic: Painter,
    val text: String
)
@Composable
fun allList():List<ListDetail>{
    return listOf(
        ListDetail(
            painterResource(R.drawable.g),
            "General Knowledge"
        ),
        ListDetail(
            painterResource(R.drawable.m),
            "Math Quiz",

            ),
        ListDetail(
            painterResource(R.drawable.s),
            "Science Quiz",
        ),
    )
}

// Quiz Screen
data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
@Composable
fun QuizApp(viewModel: QuizViewModel, onQuitQuiz: () -> Unit) {
    val question by viewModel.currentQuestion.collectAsState()
    val score by viewModel.score.collectAsState()
    val onAnswerSelected: (Int) -> Unit


    if (question != null) {
        onAnswerSelected = { selected -> viewModel.submitAnswer(selected)}
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = question!!.questionText,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp, top = 80.dp)
            )
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                question!!.options.forEachIndexed { index, option ->
                    Button(
                        onClick = { onAnswerSelected(index) },
                        colors = ButtonDefaults.buttonColors(containerColor = Orange),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = option)
                    }
                }
            }

            Text(
                text = "Score: $score",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 32.dp)
            )
            Button(
                onClick = onQuitQuiz,
                colors = ButtonDefaults.buttonColors(containerColor = Orange),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Quit Quiz")
            }
        }
    } else {
        ResultScreen(score = score, onRestart = { onQuitQuiz() })
    }
}
