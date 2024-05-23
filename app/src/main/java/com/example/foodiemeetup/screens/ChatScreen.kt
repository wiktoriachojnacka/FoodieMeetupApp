package com.example.foodiemeetup.screens
import ChatViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.foodiemeetup.models.Message
import com.example.foodiemeetup.ViewModels.LoginViewModel

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    // Stan do przechowywania aktualnej wiadomości
    var messageText by remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (messagesRef, inputBoxRef) = createRefs()

        // Wyświetl listę wiadomości
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(messagesRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(inputBoxRef.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
        ) {
            items(viewModel.messages) { message ->
                MessageBubble(message = message, currentUsername = viewModel.username)
            }
        }

        // Wyświetl pole do wprowadzania tekstu i przycisk do wysyłania
        ChatBox(
            messageText = messageText,
            onMessageChange = { messageText = it },
            onSendClick = {
                if (messageText.isNotBlank()) {
                    // Tworzymy nową wiadomość na podstawie wpisanego tekstu i aktualnego użytkownika
                    val newMessage = Message(text = messageText, author = viewModel.username)
                    // Dodajemy nową wiadomość do listy w ChatViewModel
                    viewModel.sendMessage(newMessage)
                    // Czyszczenie pola tekstowego
                    messageText = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(inputBoxRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}
@Composable
fun MessageBubble(message: Message, currentUsername: String) {
    val isFromMe = message.isFromMe(currentUsername)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .offset(x = if (isFromMe) 0.dp else 48.dp, y = 0.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 48.dp,
                    topEnd = 48.dp,
                    bottomStart = if (isFromMe) 48.dp else 0.dp,
                    bottomEnd = if (isFromMe) 0.dp else 48.dp
                )
            )
            .background(if (isFromMe) Color.Blue else Color.Gray)
            .padding(16.dp)
    ) {
        Text(text = message.text, color = Color.White)
    }
}

@Composable
fun ChatBox(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        BasicTextField(
            value = messageText,
            onValueChange = onMessageChange,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Button(onClick = onSendClick) {
            Text(text = "Send")
        }
    }
}