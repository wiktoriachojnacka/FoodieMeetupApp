package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.AvailableChat
import com.example.foodiemeetup.models.MessageRequest
import com.example.foodiemeetup.models.MessageResponse
import com.example.foodiemeetup.models.StringResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatScreenViewModel
    : ViewModel() {
    private val repository = LoginRepository()
    lateinit var messages: List<MessageResponse>

    fun getMessages(
        token: String, chatID: Int, limit: Int, offset: Int, context: Context,
        onResponse: (List<MessageResponse>) -> Unit
    ) {
        viewModelScope.launch {
            val call: Call<List<MessageResponse>> = repository.getMessages(token, chatID, offset, limit)
            call.enqueue(object : Callback<List<MessageResponse>> {
                override fun onResponse(
                    call: Call<List<MessageResponse>>,
                    response: Response<List<MessageResponse>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            messages = responseBody
                            Log.d("ChatScreenViewModel", "Messages received: $messages")
                            onResponse(messages)
                        } else {
                            Log.e("ChatScreenViewModel", "Response body is null")
                        }
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        Log.e("ChatScreenViewModel", "Error: $message")
                    }
                }

                override fun onFailure(call: Call<List<MessageResponse>>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("ChatScreenViewModel", "Failure: ${t.message}")
                }
            })
        }
    }

    fun sendMessage(token: String, context: Context, matchId: Int, message: MessageRequest) {
        val call: Call<StringResponseModel> = repository.sendMessage(token, matchId, message)
        call.enqueue(object : Callback<StringResponseModel?> {
            override fun onResponse(
                call: Call<StringResponseModel?>,
                response: Response<StringResponseModel?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val message = responseBody?.message
                    Toast.makeText(context, "Response:" + message, Toast.LENGTH_SHORT).show()
                } else {
                    val responseBody = response.errorBody()
                    val message = responseBody?.string()
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StringResponseModel?>, t: Throwable) {

            }

        })
    }
    }