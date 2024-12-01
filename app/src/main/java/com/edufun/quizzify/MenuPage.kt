package com.edufun.quizzify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.edufun.quizzify.quizFunctions.Quiz
import com.edufun.quizzify.quizFunctions.QuizViewModel
import com.edufun.quizzify.ui.theme.*


// Menu
@Composable
fun MenuScreen(
    viewModel: QuizViewModel,
    // quizzes: List<Quiz>,
    onQuizSelected: (String) -> Unit,
    onProfile: () -> Unit,
    onLogout: () -> Unit
) {
    val quizzes by viewModel.quizzes.collectAsState()
    val loading by viewModel.loading.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (loading) {
            // Show a loading spinner while quizzes are being fetched
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else if (quizzes.isEmpty()) {
            // Display a message if no quizzes are available
            Text(
                text = "No quizzes available",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            // Display the list of quizzes
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                items(quizzes) { quiz ->
                    Column(modifier = Modifier.padding(vertical = 10.dp)) {
                        Image(
                            painter = painterResource(id = quiz.coverImageResId),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(7.dp))
                        )
                        Button(
                            onClick = {
                                onQuizSelected(quiz.name)
                                println("DEBUG: Selected quiz - ${quiz.name}") // Debug log
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text(text = quiz.name)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onProfile) {
                Text("Profile")
            }
            Button(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}


