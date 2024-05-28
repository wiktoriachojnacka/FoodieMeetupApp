package com.example.foodiemeetup.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController



@Composable
fun ChatPeopleScreen(chatPeopleViewModel: ChatPeopleViewModel = viewModel(),navController: NavController) {
    val connectedUsers by chatPeopleViewModel.connectedUsers.collectAsState()

    // Logowanie liczby użytkowników
    Log.d("ChatPeopleScreen", "Number of connected users: ${connectedUsers.size}")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Available Users",
            modifier = Modifier.padding(vertical = 8.dp), // Padding dla nagłówka
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(connectedUsers) { user ->
                ChatUserItem(user = user) { clickedUser ->
                    // Obsługa kliknięcia na użytkownika
                    // Tutaj można przenieść się do czatu z tym użytkownikiem
                    navController.navigate("ChatDetail/${clickedUser.username}")
                }
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