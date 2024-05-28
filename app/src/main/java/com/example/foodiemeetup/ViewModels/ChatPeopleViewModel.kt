package com.example.foodiemeetup.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ChatPeopleViewModel : ViewModel() {
    private val _connectedUsers = MutableStateFlow<List<User>>(emptyList())
    val connectedUsers: StateFlow<List<User>> = _connectedUsers.asStateFlow()

    fun addConnectedUser(user: User) {
        _connectedUsers.value += user
    }

    fun removeConnectedUser(user: User) {
        _connectedUsers.value -= user
    }
}
/*
class ChatPeopleViewModel : ViewModel() {
    private val _connectedUsers = MutableStateFlow<List<User>>(emptyList())
    val connectedUsers: StateFlow<List<User>> = _connectedUsers

    init {
        fetchConnectedUsers()
    }

    private fun fetchConnectedUsers() {
        // Pobierz listę użytkowników z zakładki "MyEvents"
        viewModelScope.launch {
            // Przykładowe pobieranie danych, zastąp to własnym kodem
            val users = fetchUsersFromMyEvents()
            _connectedUsers.value = users
        }
    }

    private suspend fun fetchUsersFromMyEvents(): List<User> {
        // Symulacja pobierania danych, zastąp to własnym kodem pobierającym dane z odpowiedniego źródła
        return listOf(
            User(username = "asiaa"),
            User(username = "user2"),
            User(username = "user3")
        )
    }
}
*/