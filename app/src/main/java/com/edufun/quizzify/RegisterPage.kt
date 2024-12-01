package com.edufun.quizzify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edufun.quizzify.authFunctions.AuthFunctions
import com.edufun.quizzify.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

// Register Screen
@Composable
fun RegisterScreen(auth: FirebaseAuth, onRegister: () -> Unit, onBackToLogin: () -> Unit) {
    var displayName by remember { mutableStateOf("") }
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
//                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(7.dp))
                .size(250.dp)
        )
        Text(
            text = "Register",
            color = Color.Yellow,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        TextField(
            value = displayName,
            onValueChange = { displayName = it },
            singleLine = true,
            label = { Text("Display Name") },
            placeholder = { Text("John Doe") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White,),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            singleLine = true,
            label = { Text("Confirm Password") },
            placeholder = { Text("Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
//            onClick = onRegister, // TODO: Add register config
            onClick = { AuthFunctions().registerUser(auth, email, password, confirmPassword, displayName, onBackToLogin) },
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
