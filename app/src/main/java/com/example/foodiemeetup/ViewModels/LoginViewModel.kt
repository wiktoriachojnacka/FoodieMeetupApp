package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.RegisterModel
import com.example.foodiemeetup.models.RegisterResponseModel
import com.example.foodiemeetup.models.TokenResponseModel
import com.example.foodiemeetup.models.UserResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    var username: String by mutableStateOf("")
        private set
    fun registerUser(user: RegisterModel, context: Context) {
        viewModelScope.launch {
            val call: Call<RegisterResponseModel> = repository.createUser(user)
            call.enqueue(object : Callback<RegisterResponseModel> {
                override fun onResponse(
                    call: Call<RegisterResponseModel>,
                    response: Response<RegisterResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val message = responseBody?.message
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun loginUser(credentials: String, context: Context, onTokenReceived: (String) -> Unit) {
        viewModelScope.launch {
            val call: Call<TokenResponseModel> = repository.loginUser(credentials)
            call.enqueue(object : Callback<TokenResponseModel> {
                override fun onResponse(
                    call: Call<TokenResponseModel>,
                    response: Response<TokenResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val token = responseBody?.token ?: "0"
                        //Toast.makeText(context, "Hello!", Toast.LENGTH_SHORT).show()
                        onTokenReceived(token)
                        // Jeśli token jest prawidłowy, pobieramy dane użytkownika
                        if (token != "0") {
                            getUserData(token, context)
                        }
                    } else {
                        Toast.makeText(context, "Email or password are incorrect", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<TokenResponseModel>, t: Throwable) {
                    Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun getUserData(token: String, context: Context) {
        viewModelScope.launch {
            val call: Call<UserResponseModel> = repository.getUserData(token)
            call.enqueue(object : Callback<UserResponseModel> {
                override fun onResponse(
                    call: Call<UserResponseModel>,
                    response: Response<UserResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            username = responseBody.username
                        }
                    } else {
                       // Toast.makeText(context, "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponseModel>, t: Throwable) {
                    Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
