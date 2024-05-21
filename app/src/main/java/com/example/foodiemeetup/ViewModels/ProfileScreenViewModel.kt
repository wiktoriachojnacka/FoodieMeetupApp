package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.StringResponseModel
import com.example.foodiemeetup.models.UserResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileScreenViewModel : ViewModel() {
    private val repository = LoginRepository()

    fun getUserData(token: String, context: Context, onResponse: (UserResponseModel) -> Unit){
        var user: UserResponseModel = UserResponseModel()
        viewModelScope.launch {
            val call: Call<UserResponseModel> = repository.getUserData(token)
            call.enqueue(object : Callback<UserResponseModel?> {
                override fun onResponse(
                    call: Call<UserResponseModel?>,
                    response: Response<UserResponseModel?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        user = responseBody as UserResponseModel
                        onResponse(user)
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponseModel?>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

            })

        }
    }


    var isDialogShown by mutableStateOf(false)
        private set

    fun onDeleteUserClick(){
        isDialogShown = true
    }

    fun onDismissDialog(){
        isDialogShown = false
    }

    fun postUserDelete(token: String, context: Context){
        viewModelScope.launch {
            val call: Call<StringResponseModel> = repository.postUserDelete(token)
            call.enqueue(object : Callback<StringResponseModel?> {
                override fun onResponse(
                    call: Call<StringResponseModel?>,
                    response: Response<StringResponseModel?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val message = responseBody?.message
                        Toast.makeText(context, "Account deleted:" + message, Toast.LENGTH_SHORT).show()
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, "Response:" + message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<StringResponseModel?>, t: Throwable) {
                    //Toast.makeText(context, "Err:" + t.toString(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Account has been deleted", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}