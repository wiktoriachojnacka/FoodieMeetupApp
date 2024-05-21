package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.AvailableMatchesResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeMatchScreenViewModel : ViewModel() {
    private val repository = LoginRepository()
    var aMatches: List<AvailableMatchesResponseModel> by mutableStateOf(listOf())
    private val _isLoading = mutableStateOf(true)
    val isLoading: Boolean
        get() = _isLoading.value

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getAvailableMatches(token: String, context: Context, placeName: String){
        viewModelScope.launch {
            val call: Call<List<AvailableMatchesResponseModel>> = repository.getAvailableMatches(token, placeName)
            call.enqueue(object : Callback<List<AvailableMatchesResponseModel>> {
                override fun onResponse(
                    call: Call<List<AvailableMatchesResponseModel>>,
                    response: Response<List<AvailableMatchesResponseModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { aMatches = it }
                        setLoading(false)
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<AvailableMatchesResponseModel>>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}