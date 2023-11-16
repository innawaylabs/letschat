@file:OptIn(ExperimentalMaterial3Api::class)

package com.innawaylabs.letschat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.innawaylabs.letschat.ui.theme.LetsChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatApplication("Android")
                }
            }
        }
    }
}

@Composable
fun ChatApplication(name: String, modifier: Modifier = Modifier) {
    val usersList = listOf("Ravi", "Sailu", "Rohan", "Suhaas")
    val context = LocalContext.current

    Column {
        TopAppBar(
            title = { Text("Let's Chat") },
            modifier = Modifier.padding(16.dp),
            navigationIcon = {
                IconButton(onClick = { /* Go back */ }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* Do something */ }) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
                IconButton(onClick = { /* Do something */ }) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = "Share"
                    )
                }
            }
        )
        Column {
            LazyColumn(
                Modifier
                    .padding(16.dp)
                    .border(1.dp, Color.Black)) {
                items(usersList.size) { index ->
                    Text(text = usersList[index],
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                openConversation(context)
                            })
                    Divider()
                }
            }
        }
    }
}

fun openConversation(context: Context) {
    context.startActivity(Intent(context, Conversation::class.java))
}

@Preview(showBackground = true)
@Composable
fun ChatApplicationPreview() {
    LetsChatTheme {
        ChatApplication("Android")
    }
}