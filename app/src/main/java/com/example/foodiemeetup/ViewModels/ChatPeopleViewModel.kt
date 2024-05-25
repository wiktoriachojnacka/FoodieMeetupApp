package com.example.foodiemeetup.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.foodiemeetup.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

