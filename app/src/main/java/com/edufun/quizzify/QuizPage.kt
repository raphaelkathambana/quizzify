package com.edufun.quizzify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.edufun.quizzify.quizFunctions.QuizViewModel
import com.edufun.quizzify.ui.theme.Orange
import com.edufun.quizzify.ui.theme.Purple40



// Quiz Screen
data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
@Composable
fun QuizApp(viewModel: QuizViewModel, onQuitQuiz: () -> Unit) {
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val score by viewModel.score.collectAsState()


    if (currentQuestion != null) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
//                .padding(16.dp)
        ) {
            Column {
                BackButton(onQuitQuiz)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currentQuestion!!.questionText,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp, top = 80.dp)
                    )

                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        currentQuestion!!.options.forEachIndexed { index, option ->
                            Button(
                                onClick = { viewModel.submitAnswer(index) },
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
//                    Button(
//                        onClick = onQuitQuiz,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 16.dp)
//                    ) {
//                        Text("Quit Quiz")
//                    }
                }
            }
        }
    } else {
        // Debugging log to identify the state
        LaunchedEffect(Unit) {
            println("DEBUG: currentQuestion is null, navigating to results screen")
        }
        ResultScreen(score = score, onRestart = onQuitQuiz)
    }
}

@Composable
fun BackButton(onQuitQuiz: () -> Unit) {
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Purple40),

        ) {

        IconButton(
//            onClick = onQuitQuiz,
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Localized description"
            )
        }
        Text(
            text = "Quiz",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(12.dp)
        )
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Triggered when clicking outside the dialog
            title = {
                Text(text = "Confirm Action")
            },
            text = {
                Text(text = "Are you sure you want to quit?")
            },
            confirmButton = {
                Button(
                    onClick = onQuitQuiz,
                    colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                ) {

                    Text("Quit?", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Dismiss", color = Orange)
                }
            }
        )
    }
}