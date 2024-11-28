package com.edufun.quizzify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edufun.quizzify.ui.theme.QuizzifyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizzifyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    AppNavigator()
                }
            }
        }
    }
}

// Navigation
sealed class AppScreen {
    object Menu : AppScreen()
    object Quiz : AppScreen()
    object Login : AppScreen()
    object Register : AppScreen()
    object Profile : AppScreen()
}
@Composable
fun AppNavigator(viewModel: QuizViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Login) }

    // Handle back button behavior
    BackHandler(enabled = currentScreen != AppScreen.Login) {
        when (currentScreen) {
            is AppScreen.Menu -> currentScreen = AppScreen.Login  // From Menu to Login
            is AppScreen.Quiz -> currentScreen = AppScreen.Menu    // From Quiz to Menu
            is AppScreen.Profile -> currentScreen = AppScreen.Menu // From Profile to Menu
            is AppScreen.Register -> currentScreen = AppScreen.Login // From Register to Login
            else -> {} // No action on Login, app will exit normally
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentScreen) {
            is AppScreen.Login -> LoginScreen(
                onLogin = { currentScreen = AppScreen.Menu },
                onRegisterNavigate = { currentScreen = AppScreen.Register }
            )
            is AppScreen.Register -> RegisterScreen(
                onRegister = { currentScreen = AppScreen.Menu },
                onBackToLogin = { currentScreen = AppScreen.Login }
            )
            is AppScreen.Menu -> DrawerTab(
                onQuizSelected = { quizName ->
                    viewModel.loadQuiz(quizName)
                    currentScreen = AppScreen.Quiz
                },
                onLogout = { currentScreen = AppScreen.Login },
                onProfile = { currentScreen = AppScreen.Profile}
            )
            is AppScreen.Quiz -> QuizApp(
                viewModel = viewModel,
                onQuitQuiz = { currentScreen = AppScreen.Menu }
            )
            is AppScreen.Profile -> ProfileScreen(
                    name = "John Doe",
                    profileImage = R.drawable.image,
                onMenu = { currentScreen = AppScreen.Menu}

                )
        }
    }
}