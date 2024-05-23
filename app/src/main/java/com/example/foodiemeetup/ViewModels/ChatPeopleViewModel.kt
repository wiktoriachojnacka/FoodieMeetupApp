package com.example.foodiemeetup.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.foodiemeetup.models.User

class ChatPeopleViewModel : ViewModel() {
    var connectedUsers: List<User> by mutableStateOf(emptyList())

    fun addConnectedUser(user: User) {
        connectedUsers += user
    }

    fun removeConnectedUser(user: User) {
        connectedUsers -= user
    }

}

