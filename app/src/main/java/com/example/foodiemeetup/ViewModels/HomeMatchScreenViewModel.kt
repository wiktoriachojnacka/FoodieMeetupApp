package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.AvaiableMatchesResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeMatchScreenViewModel : ViewModel() {
    private val repository = LoginRepository()
    var aMatches: List<AvaiableMatchesResponseModel> by mutableStateOf(listOf())

    fun getAvaiableMatches(context: Context, placeName: String){
        viewModelScope.launch {
            val call: Call<List<AvaiableMatchesResponseModel>> = repository.getAvaiableMatches(placeName)
            call.enqueue(object : Callback<List<AvaiableMatchesResponseModel>> {
                override fun onResponse(
                    call: Call<List<AvaiableMatchesResponseModel>>,
                    response: Response<List<AvaiableMatchesResponseModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { aMatches = it }
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<AvaiableMatchesResponseModel>>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}