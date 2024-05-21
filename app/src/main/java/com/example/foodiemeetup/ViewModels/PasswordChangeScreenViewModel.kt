package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.PasswordModel
import com.example.foodiemeetup.models.StringResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordChangeScreenViewModel : ViewModel() {
    private val repository = LoginRepository()

    fun postPasswordChange(token: String, credentials: String, context: Context){
        val password = PasswordModel(credentials)
        viewModelScope.launch {
            val call: Call<StringResponseModel> = repository.postPasswordChange(token, password)
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
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
                }

            })

        }
    }
}