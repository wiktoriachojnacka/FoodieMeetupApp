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
import com.example.foodiemeetup.models.UserMatchesResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsScreenViewModel: ViewModel() {
    private val repository = LoginRepository()
    lateinit var userMatches: List<UserMatchesResponseModel> //by mutableStateOf(listOf())
    var isDeleteButtonShown by mutableStateOf(false)

    fun getUserMatches(token: String, context: Context, openMatches: String, onResponse: (List<UserMatchesResponseModel>) -> Unit) {
        viewModelScope.launch {
            val call: Call<List<UserMatchesResponseModel>> = repository.getUserMatches(token, openMatches)
            call.enqueue(object : Callback<List<UserMatchesResponseModel>> {
                override fun onResponse(
                    call: Call<List<UserMatchesResponseModel>>,
                    response: Response<List<UserMatchesResponseModel>>
                ) {
                    if (response.isSuccessful) {
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

    fun postDeleteMatch(token: String, context: Context, matchId: Int){
        val call: Call<StringResponseModel> = repository.postDeleteMatch(token, matchId)
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
                //Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "Event has been deleted", Toast.LENGTH_SHORT).show()
            }

        })

    }
}