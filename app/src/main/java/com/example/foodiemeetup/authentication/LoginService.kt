package com.example.foodiemeetup.authentication

import com.example.foodiemeetup.models.RegisterModel
import com.example.foodiemeetup.models.RegisterResponseModel
import com.example.foodiemeetup.models.TokenResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST("register")
    fun createUsers(@Body usersinfo: RegisterModel): Call<RegisterResponseModel>

    @POST("token")
    fun loginUser(@Header("Authorization") credentials: String): Call<TokenResponseModel>

}

