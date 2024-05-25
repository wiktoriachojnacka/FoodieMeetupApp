package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
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
    lateinit var userMatches: List<UserMatchesResponseModel> //by mutableStateOf(listOf())

    fun getUserMatches(token: String, context: Context, onResponse: (List<UserMatchesResponseModel>) -> Unit) {
        viewModelScope.launch {
            val call: Call<List<UserMatchesResponseModel>> = repository.getUserMatches(token)
            call.enqueue(object : Callback<List<UserMatchesResponseModel>> {
                override fun onResponse(
                    call: Call<List<UserMatchesResponseModel>>,
                    response: Response<List<UserMatchesResponseModel>>
                ) {
                    if (response.isSuccessful) {
                        //response.body()?.let { userMatches = it }
                        val responseBody = response.body()
                        userMatches = responseBody as List<UserMatchesResponseModel>
                        onResponse(userMatches)
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