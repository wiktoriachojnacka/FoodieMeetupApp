package com.example.foodiemeetup.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodiemeetup.authentication.LoginRepository

class EventsScreenViewModel: ViewModel() {
    private val repository = LoginRepository()

    fun getUserMatches(token: String, context: Context) {
    }
}