package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.AvailableChat
import com.example.foodiemeetup.models.StringResponseModel
import com.example.foodiemeetup.models.UserMatchesResponseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatPeopleViewModel : ViewModel() {
    private val repository = LoginRepository()
    lateinit var availableChats: List<AvailableChat>

    fun getAvailableChats(token: String, context: Context, onResponse: (List<AvailableChat>) -> Unit) {
        viewModelScope.launch {
            val call: Call<List<AvailableChat>> = repository.getChats(token)
            call.enqueue(object : Callback<List<AvailableChat>> {
                override fun onResponse(
                    call: Call<List<AvailableChat>>,
                    response: Response<List<AvailableChat>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        availableChats = responseBody as List<AvailableChat>
                        onResponse(availableChats)
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<AvailableChat>>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}