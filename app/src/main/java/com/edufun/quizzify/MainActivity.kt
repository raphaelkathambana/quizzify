package com.edufun.quizzify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.DragHandle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.edufun.quizzify.ui.theme.QuizzifyTheme
import com.edufun.quizzify.ui.theme.*

import kotlinx.coroutines.launch


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

// AppBar
@Composable
fun AppBar(drawerState: DrawerState, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    Row(modifier = modifier
        .fillMaxWidth()
        .background(Purple40),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        IconButton(
            onClick = {
                scope.launch{
                    drawerState.open()
                }
            }) {
            Icon(
                Icons.Outlined.DragHandle,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
            )
        }
    }
}

// Drawer
@Composable
fun DrawerTab(onQuizSelected: (String) -> Unit, onLogout: () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val dockerWidth = LocalConfiguration.current.screenWidthDp * 0.75

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Black,
                drawerTonalElevation = Dp.Hairline,
                modifier = Modifier
                    .width(dockerWidth.dp)
                    .drawWithContent {
                        drawContent()
                        drawLine(
                            color = Color.White,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = 0.3.dp.toPx()
                        )
                    }
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Image(
                        painter = painterResource(R.drawable.image),
                        contentDescription = null,
                        alignment = Alignment.TopStart,
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(40.dp)
                            .clip(shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Beth!!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                    Text(
                        text = "@JustMeHopeless",
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row() {
                            Text(
                                text = "549",
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier
                            )
                            Text(
                                text = " Following",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))

                        Row {
                            Text(
                                text = "16",
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier
                            )
                            Text(
                                text = " Followers",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    item {
                        Row (){
                            Icon(
                                Icons.Outlined.Person,
                                tint = Color.White,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                            )
                            Text(
                                text = "Profile",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .height(50.dp)
                                    .padding(start = 20.dp)
                            )
                        }

                    }
                    item {
                        Row {
                            Icon(
                                Icons.Outlined.BookmarkBorder,
                                tint = Color.White,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                            )
                            Text(
                                text = "Bookmark",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .height(50.dp)
                                    .padding(start = 20.dp)

                            )
                        }
                    }
                    item {
                        Row {
                            Icon(
                                Icons.Outlined.List,
                                tint = Color.White,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                            )
                            Text(
                                text = "Lists",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .height(50.dp)
                                    .padding(start = 20.dp)
                            )
                        }
                    }
                    item {
                        Row {
                            Icon(
                                Icons.Outlined.Mic,
                                tint = Color.White,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                            )
                            Text(
                                text = "Spaces",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .height(50.dp)
                                    .padding(start = 20.dp)
                            )
                        }
                    }
                    item {
                        Row {
                            Icon(
                                Icons.Outlined.MonetizationOn,
                                tint = Color.White,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                            )
                            Text(
                                text = "Monetization",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .height(50.dp)
                                    .padding(start = 20.dp)
                            )
                        }
                    }

                }
            }
        },
        drawerState = drawerState
    ) {
        Column{
            AppBar(drawerState)
            MenuScreen(
                onQuizSelected = onQuizSelected,
                onLogout = onLogout
            )
        }
    }
}

// Navigation
sealed class AppScreen {
    object Menu : AppScreen()
    object Quiz : AppScreen()
    object Login : AppScreen()
    object Register : AppScreen()
}
@Composable
fun AppNavigator(viewModel: QuizViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Login) }

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
                onLogout = { currentScreen = AppScreen.Login }
            )

//            MenuScreen(
//                onQuizSelected = { quizName ->
//                    viewModel.loadQuiz(quizName)
//                    currentScreen = AppScreen.Quiz
//                },
//                onLogout = { currentScreen = AppScreen.Login }
//            )

//
            is AppScreen.Quiz -> QuizApp(
                viewModel = viewModel,
                onQuitQuiz = { currentScreen = AppScreen.Menu }
            )
        }
    }
}

// Login Screen
@Composable
fun LoginScreen(onLogin: () -> Unit, onRegisterNavigate: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            alignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(7.dp))
        )
        Text(
            text = "Login",
            color = Color.Yellow,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            label = { Text("Email Address") },
            placeholder = { Text("example@domain.com") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White,),
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            label = { Text("Password") },
            placeholder = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
        )
        Button(
            onClick = onLogin,
            colors = ButtonDefaults.buttonColors(containerColor = Purple40),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Login", color = Color.White)
        }
        TextButton(onClick = onRegisterNavigate) {
            Text(text = "Don't have an account? Register", color = Orange)
        }
    }
}

// Register Screen
@Composable
fun RegisterScreen(onRegister: () -> Unit, onBackToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            alignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(7.dp))
        )
        Text(
            text = "Register",
            color = Color.Yellow,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            label = { Text("Email Address") },
            placeholder = { Text("example@domain.com") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White,),
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            label = { Text("Password") },
            placeholder = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
        )

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            singleLine = true,
            label = { Text("Password") },
            placeholder = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
        )
        Button(
            onClick = onRegister,
            colors = ButtonDefaults.buttonColors(containerColor = Purple40),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Register", color = Color.White)
        }

        TextButton(onClick = onBackToLogin) {
            Text(text = "Already have an account? Login", color = Orange)
        }
    }
}

// Menu
@Composable
fun MenuScreen(onQuizSelected: (String) -> Unit, onLogout: () -> Unit) {
    val allList = allList()
//    val availableQuizzes = listOf("General Knowledge", "Math Quiz", "Science Quiz")
//    val availableImages = listOf(painterResource(R.drawable.GN), painterResource(R.drawable.M), painterResource(R.drawable.S))
    Column(
        modifier = Modifier
            .fillMaxSize(),
//            .background(Purple),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple40),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
            Text(
                text = "Welcome to Quizzify",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                        ) {
                            Text(text = quizName.text)
                        }
                    }

            }
        }
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Logout")
        }
    }
}

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

// Result Screen
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