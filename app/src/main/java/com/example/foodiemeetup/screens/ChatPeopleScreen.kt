package com.example.foodiemeetup.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodiemeetup.ViewModels.ChatPeopleViewModel
import com.example.foodiemeetup.models.User
import androidx.compose.runtime.collectAsState
/*

@Composable
fun ChatPeopleScreen(chatPeopleViewModel: ChatPeopleViewModel = viewModel()) {
    val connectedUsers by chatPeopleViewModel.connectedUsers.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(connectedUsers) { user ->
            ChatUserItem(user = user) { clickedUser ->
                // Obsługa kliknięcia na użytkownika
                // Tutaj można przenieść się do czatu z tym użytkownikiem
            }
        }
    }
}

@Composable
fun ChatUserItem(user: User, onClick: (User) -> Unit) {
    Text(
        text = user.username,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(user) }
    )
}
*/