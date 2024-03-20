package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.models.UserResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileScreenViewModel : ViewModel()  {
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
                        Toast.makeText(context, "Response from API not successfull", Toast.LENGTH_SHORT).show()
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

}