package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.PreferencesResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferencesScreenViewModel  : ViewModel()  {
    private val repository = LoginRepository()
    var preferences: PreferencesResponseModel = PreferencesResponseModel()
    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean
        get() = _isLoading.value

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getUserPreferences(token: String, context: Context, onResponse: (PreferencesResponseModel) -> Unit){
        setLoading(true)
        viewModelScope.launch {
            val call: Call<PreferencesResponseModel> = repository.getUserPreferences(token)
            call.enqueue(object : Callback<PreferencesResponseModel?> {
                override fun onResponse(
                    call: Call<PreferencesResponseModel?>,
                    response: Response<PreferencesResponseModel?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        preferences = responseBody as PreferencesResponseModel
                        onResponse(preferences)
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    setLoading(false)
                }

                override fun onFailure(call: Call<PreferencesResponseModel?>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

            })

        }
    }

}