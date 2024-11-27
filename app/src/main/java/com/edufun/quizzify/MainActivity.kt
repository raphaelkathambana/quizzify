package com.edufun.quizzify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.edufun.quizzify.ui.theme.QuizzifyTheme
import com.edufun.quizzify.ui.theme.Orange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizzifyTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator(viewModel: QuizViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Menu) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (currentScreen) {
            is AppScreen.Menu -> MenuScreen(
                onQuizSelected = { quizName ->
                    viewModel.loadQuiz(quizName)
                    currentScreen = AppScreen.Quiz
                }
            )
            is AppScreen.Quiz -> QuizApp(
                viewModel = viewModel,
                onQuitQuiz = { currentScreen = AppScreen.Menu }
            )
        }
    }
}

// Screens Enum for Navigation
sealed class AppScreen {
    object Menu : AppScreen()
    object Quiz : AppScreen()
}
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

@Composable
fun MenuScreen(onQuizSelected: (String) -> Unit) {
    val allList = allList()
//    val availableQuizzes = listOf("General Knowledge", "Math Quiz", "Science Quiz")
//    val availableImages = listOf(painterResource(R.drawable.GN), painterResource(R.drawable.M), painterResource(R.drawable.S))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select a Quiz",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        LazyColumn {
            items (items = allList){
                quizName ->
                    Column(modifier = Modifier.padding(vertical = 10.dp)) {
                        Image(
                            painter = quizName.pic,
                            contentScale = ContentScale.FillWidth,
                            contentDescription = null,
                            alignment = Alignment.TopStart,
                            modifier = Modifier
//                        .width((screenWidth*0.9).dp)
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(7.dp))
                        )
                        Button(
                            onClick = { onQuizSelected(quizName.text) },
                            colors = ButtonDefaults.buttonColors(containerColor = Orange),
                            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomEnd = 15.dp, bottomStart = 15.dp),
                            modifier = Modifier
                                .fillMaxWidth()
//                                .border(BorderStroke)
//                                .padding(vertical = 8.dp)
                        ) {
                            Text(text = quizName.text)
                        }
                    }

            }
        }
    }
}

// Quiz Screen (Enhanced with Quit Button)
@Composable
fun QuizApp(
    viewModel: QuizViewModel,
    onQuitQuiz: () -> Unit
) {
    val question by viewModel.currentQuestion.collectAsState()
    val score by viewModel.score.collectAsState()

    if (question != null) {
        QuizScreen(
            question = question!!,
            onAnswerSelected = { selected ->
                viewModel.submitAnswer(selected)
            },
            onQuitQuiz = onQuitQuiz,
            score = score
        )
    } else {
        ResultScreen(score = score, onRestart = { onQuitQuiz() })
    }
}

@Composable
fun QuizScreen(
    question: Question,
    onAnswerSelected: (Int) -> Unit,
    onQuitQuiz: () -> Unit,
    score: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = question.questionText,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp, top = 80.dp)
        )
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            question.options.forEachIndexed { index, option ->
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
}


data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            QuizzifyTheme {
//                QuizApp()
//            }
//        }
//    }
//}
//
//@Composable
//fun QuizApp(viewModel: QuizViewModel = viewModel()) {
//    val question by viewModel.currentQuestion.collectAsState()
//    val score by viewModel.score.collectAsState()
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        if (question != null) {
//            QuizScreen(
//                question = question!!,
//                onAnswerSelected = { selected ->
//                    viewModel.submitAnswer(selected)
//                },
//                score = score
//            )
//        } else {
//            ResultScreen(score = score, onRestart = { viewModel.restartQuiz() })
//        }
//    }
//}
//
//@Composable
//fun QuizScreen(
//    question: Question,
//    onAnswerSelected: (Int) -> Unit,
//    score: Int
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = question.questionText,
//            style = MaterialTheme.typography.headlineMedium,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(bottom = 32.dp, top = 80.dp)
//        )
//        Column(verticalArrangement = Arrangement.SpaceBetween) {
//            question.options.forEachIndexed { index, option ->
//                Button(
//                    onClick = { onAnswerSelected(index) },
//                    colors = ButtonDefaults.buttonColors(containerColor = Orange),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                ) {
//                    Text(text = option)
//                }
//            }
//        }
//
//        Text(
//            text = "Score: $score",
//            style = MaterialTheme.typography.bodyMedium,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(top = 32.dp)
//        )
//    }
//}
//
@Composable
fun ResultScreen(score: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz Finished! Your Score: $score",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(onClick = onRestart, colors = ButtonDefaults.buttonColors(containerColor = Orange)) {
            Text(text = "Restart Quiz")
        }
    }
}