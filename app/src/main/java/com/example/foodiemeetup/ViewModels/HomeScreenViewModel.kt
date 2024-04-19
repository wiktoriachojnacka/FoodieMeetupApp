package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiemeetup.authentication.LoginRepository
import com.example.foodiemeetup.models.MapPointsResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenViewModel : ViewModel() {
    private val repository = LoginRepository()
    var pointss: List<MapPointsResponseModel> by mutableStateOf(listOf())
    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean
        get() = _isLoading.value

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    fun getMapPoints(context: Context){
        setLoading(true) // Ustaw stan ładowania na true przed pobieraniem danych

        viewModelScope.launch {
            val call: Call<List<MapPointsResponseModel>> = repository.getMapPoints()
            call.enqueue(object : Callback<List<MapPointsResponseModel>> {
                override fun onResponse(
                    call: Call<List<MapPointsResponseModel>>,
                    response: Response<List<MapPointsResponseModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { pointss = it }
                    } else {
                        val responseBody = response.errorBody()
                        val message = responseBody?.string()
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    setLoading(false) // Ustaw stan ładowania na false po zakończeniu zapytania
                }

                override fun onFailure(call: Call<List<MapPointsResponseModel>>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    setLoading(false) // Ustaw stan ładowania na false po zakończeniu zapytania
                }

            })
        }
    }

    var pointName by mutableStateOf("")
    var pointAddress by mutableStateOf("")

    var isDialogShown by mutableStateOf(false)
        private set

    fun onPointClick(pointNamee: String, pointAddresss: String){
        isDialogShown = true
        pointName = pointNamee
        pointAddress = pointAddresss
    }

    fun onDismissDialog(){
        isDialogShown = false
    }
}