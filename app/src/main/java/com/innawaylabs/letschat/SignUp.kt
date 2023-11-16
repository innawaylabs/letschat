package com.innawaylabs.letschat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.innawaylabs.letschat.ui.theme.LetsChatTheme

class SignUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpScreen()
                }
            }
        }
    }
}

@Composable
fun SignUpScreen() {
    val context = LocalContext.current
    var name by remember { mutableStateOf("Ravi") }
    var username by remember { mutableStateOf("ravi@gmail.com") }
    var password by remember { mutableStateOf("123456") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
        OutlinedTextField(
            onValueChange = { name = it },
            value = name,
            enabled = true,
            label = { Text("Name") }
        )
        OutlinedTextField(
            onValueChange = { username = it },
            value = username,
            enabled = true,
            label = { Text("Username") }
        )
        OutlinedTextField(
            onValueChange = { password = it },
            value = password,
            enabled = true,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = {
            validateSignUp(
                context = context,
                name = name,
                username = username,
                password = password
            )
        }, Modifier.padding(18.dp)) {
            Text("Sign Up")
        }
        Button(onClick = {
            openLogInActivity(context)
        }) {
            Text("Log In")
        }
    }
}

fun openMainActivity(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    context.startActivity(intent)
}

fun openLogInActivity(context: Context) {
    val intent = Intent(context, LogIn::class.java)
    context.startActivity(intent)
}

fun validateSignUp(context: Context, name: String, username: String, password: String) {
    val mAuth = FirebaseAuth.getInstance()
    mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener() { task ->
        if (task.isSuccessful) {
            addUserToDatabase(name, username, mAuth.currentUser?.uid!!)
            openMainActivity(context)
        } else {
            Toast.makeText(
                context,
                "Sign up failed. Please try again differently!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

fun addUserToDatabase(name: String, username: String, uid: String) {
    val mDbRef = FirebaseDatabase.getInstance().getReference()

    mDbRef.child("users").child(uid).setValue(User(name, username, uid))
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}