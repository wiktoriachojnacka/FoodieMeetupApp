package com.example.foodiemeetup.screens

import android.icu.text.SimpleDateFormat
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.MatchUsers
import com.example.foodiemeetup.components.MatchedItem
import com.example.foodiemeetup.models.AvailableChat
import com.example.foodiemeetup.models.UserMatchesResponseModel


@Composable
fun ChatPeopleScreen(viewModel: ChatPeopleViewModel, navController: NavController) {
    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }
    var userMatches: List<AvailableChat> by remember { mutableStateOf(listOf()) }

    viewModel.getAvailableChats(token, context) { uM -> userMatches = uM }

    Column {
        Spacer(modifier = Modifier.height(28.dp))
        HeadingTextComponent(value = "Chats")
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(userMatches) { chat ->
                MatchUsers(
                    username = chat.username,
                    onButtonClicked = {
                        Log.d("abc", "EventsScreen: " + chat.chat.chatId)
                        navController.navigate("chatDetail/${chat.chat.chatId}/${chat.username}")
                    },
                    isEnabled = true
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}