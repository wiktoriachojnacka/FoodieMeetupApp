package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.PreferencesResponseModel
import com.example.foodiemeetup.models.StringResponseModel
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

    fun postDeletePreferences(token: String, context: Context, preferencesId: Int){
        viewModelScope.launch {
            val call: Call<StringResponseModel> = repository.postDeletePreferences(token, preferencesId)
            call.enqueue(object : Callback<StringResponseModel?> {
                override fun onResponse(
                    call: Call<StringResponseModel?>,
                    response: Response<StringResponseModel?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val message = responseBody?.message
                        //Toast.makeText(context, "Preferences deleted:" + message, Toast.LENGTH_SHORT).show()
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, "Response:" + message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<StringResponseModel?>, t: Throwable) {
                    //Toast.makeText(context, "Preferences has been deleted", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun postCreatePreferences(token: String, context: Context, maxAge: Int, minAge: Int, gender: String){
        val city = "Torun"
        val placeType = "Polish"
        val timeOfDay = "Afternoon"
        viewModelScope.launch {
            val call: Call<StringResponseModel> = repository.postCreatePreferences(token, city, placeType, maxAge, minAge, gender, timeOfDay)
            call.enqueue(object : Callback<StringResponseModel?> {
                override fun onResponse(
                    call: Call<StringResponseModel?>,
                    response: Response<StringResponseModel?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val message = responseBody?.message
                        Toast.makeText(context, "Preferences added:" + message, Toast.LENGTH_SHORT).show()
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, "Response:" + message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<StringResponseModel?>, t: Throwable) {
                    Toast.makeText(context, "Preferences has been updated.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}