package com.example.foodiemeetup.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodiemeetup.ViewModels.ChatPeopleViewModel
import com.example.foodiemeetup.ViewModels.ChatScreenViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.MatchUsers
import com.example.foodiemeetup.models.AvailableChat
import com.example.foodiemeetup.models.MessageRequest
import com.example.foodiemeetup.models.MessageResponse
import com.example.foodiemeetup.ui.theme.Primary
import com.example.foodiemeetup.ui.theme.Secondary

@Composable
fun ChatScreen(viewModel: ChatScreenViewModel, chatId: Int, username : String) {
    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }
    var userMatches: List<MessageResponse> by remember { mutableStateOf(listOf()) }
    val listState = rememberLazyListState()

    viewModel.getMessages(token, chatId, 100, 0, context) { uM -> userMatches = uM }

    LaunchedEffect(userMatches) {
        if (userMatches.isNotEmpty()) {
            listState.scrollToItem(userMatches.size - 1)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            HeadingTextComponent(value = username)
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {
                items(userMatches) { chat ->
                    ChatMessage(chat,username)
                }
            }
            MessageInput { messageRequest ->
                viewModel.sendMessage(token, context, chatId, MessageRequest(messageRequest))
                viewModel.getMessages(token, chatId, 100, 0, context) { uM -> userMatches = uM }
            }
        }
    }
}

@Composable
fun ChatMessage(message: MessageResponse, username: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.username == username) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier

                .background(
                    if (message.username == username)
                        Brush.linearGradient(
                            colors = listOf(Secondary, Primary) // Gradient for LightGray
                        )
                    else
                        Brush.linearGradient(
                            colors = listOf(Color(140, 109, 189), Color(91, 54, 150)) // Gradient for Blue
                        )
                )
                .padding(8.dp)
        ) {
            Text(
                text = message.message,
                color = if (message.username == username) Color.Black else Color.White
            )
        }
    }
}
@Composable
fun MessageInput(onMessageSent: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .weight(1f)
                .background(Color.Gray, RoundedCornerShape(8.dp)),
            placeholder = { Text("Type your message...") },
            maxLines = 4
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                onMessageSent(text.text)
                text = TextFieldValue("") // Clear text field after sending message
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Send")
        }
    }
}