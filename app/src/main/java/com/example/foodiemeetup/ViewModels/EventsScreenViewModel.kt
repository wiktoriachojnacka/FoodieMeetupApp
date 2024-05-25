package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.UserMatchesResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsScreenViewModel: ViewModel() {
    private val repository = LoginRepository()
    var userMatches: List<UserMatchesResponseModel> by mutableStateOf(listOf())
    private val _isLoading = mutableStateOf(true)
    val isLoading: Boolean
        get() = _isLoading.value

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getUserMatches(token: String, context: Context) {
        viewModelScope.launch {
            val call: Call<List<UserMatchesResponseModel>> = repository.getUserMatches(token)
            call.enqueue(object : Callback<List<UserMatchesResponseModel>> {
                override fun onResponse(
                    call: Call<List<UserMatchesResponseModel>>,
                    response: Response<List<UserMatchesResponseModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { userMatches = it }
                        setLoading(false)
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<UserMatchesResponseModel>>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}