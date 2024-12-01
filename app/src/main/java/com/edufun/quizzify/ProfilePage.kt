package com.edufun.quizzify


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edufun.quizzify.quizFunctions.QuizViewModel
import com.edufun.quizzify.ui.theme.*
import com.google.firebase.auth.FirebaseUser

// Profile Page Composable
@Composable
fun ProfileScreen(
    viewModel: QuizViewModel,
    current: FirebaseUser?,
    profileImage: Int,
    onMenu: () -> Unit
) {
    // State to hold stats
    val stats = remember { mutableStateOf<Map<String, Any?>>(emptyMap()) }

    // Fetch user stats
    LaunchedEffect(Unit) {
        stats.value = viewModel.statsRepository.getUserStats()
    }

    // Extract data from stats
    val quizzesDone = stats.value["quizzesDone"] as? List<Map<String, Any>> ?: emptyList()
    val name = current?.displayName ?: "Unknown User"
    val email = current?.email ?: "No email provided"

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            ProfileBackButton(onMenu)

            // Profile Image and Details
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Profile Image
                Image(
                    painter = painterResource(id = profileImage),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Name and Email
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = email,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Quiz History Section
                Text(
                    text = "Quizzes Done: ${quizzesDone.size}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = Orange
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quiz History List
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Orange.copy(alpha = 0.6f))
                        .padding(16.dp)
                ) {
                    quizzesDone.forEach { quiz ->
                        Text(
                            text = "${quiz["quizName"]}: ${quiz["lastScore"]}%",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun ProfileBackButton(onMenu: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple40)
    ) {
        IconButton(
            onClick = onMenu,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Localized description"
            )
        }
        Text(
            text = "Profile Page",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(12.dp)
        )
    }
}
