package com.innawaylabs.letschat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.innawaylabs.letschat.ui.theme.LetsChatTheme

class LogIn : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            LetsChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LogInScreen(
                        {
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        },
                        {
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LogInScreen(onLoginSuccessful: () -> Unit, onLoginFailure: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Let's Chat",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(top = 16.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )
        )
        Button(
            onClick = {
                if (validateLogin(username, password)) {
                    onLoginSuccessful()
                } else {
                    onLoginFailure()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
            // , colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Text("Log In")
        }
        Button(
            onClick = {
                openSignUpActivity(context)
            },
            modifier = Modifier.padding(top = 16.dp)
            // , colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Text("Sign Up")
        }
    }
}

fun openSignUpActivity(context: Context) {
    val intent = Intent(context, SignUp::class.java)
    context.startActivity(intent)
}

fun validateLogin(username: String, password: String): Boolean {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    return true
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val context = LocalContext.current

    LogInScreen(
        {
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
        },
        {
            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
        }
    )
}
