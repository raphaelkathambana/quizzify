package com.edufun.quizzify.authFunctions

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AuthFunctions {
    fun loginUser(auth: FirebaseAuth, email: String, password: String, onLogin: () -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(auth.app.applicationContext, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(auth.app.applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                    onLogin()
                } else {
                    Toast.makeText(auth.app.applicationContext, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun registerUser(auth: FirebaseAuth, email: String, password: String, confirmPassword: String, onBackToLogin: () -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(auth.app.applicationContext, "Please fill out all fields!", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword){
            Toast.makeText(auth.app.applicationContext, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(auth.app.applicationContext, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    onBackToLogin()
                } else {
                    Toast.makeText(auth.app.applicationContext, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}