package com.example.foodiemeetup.authentication

import com.example.foodiemeetup.models.MapPointsResponseModel
import com.example.foodiemeetup.models.RegisterModel
import com.example.foodiemeetup.models.RegisterResponseModel
import com.example.foodiemeetup.models.StringResponseModel
import com.example.foodiemeetup.models.TokenResponseModel
import com.example.foodiemeetup.models.UserResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST("register")
    fun createUsers(@Body usersinfo: RegisterModel): Call<RegisterResponseModel>

    @POST("token")
    fun loginUser(@Header("Authorization") credentials: String): Call<TokenResponseModel>

    @GET("user")
    fun getUserData(@Header("Authorization") token: String): Call<UserResponseModel>

    @POST("user/password")
    fun postPasswordChange(@Header("Authorization") token: String, @Body credentials: String): Call<StringResponseModel>

    @GET("places")
    fun getMapPoints(): Call<List<MapPointsResponseModel>>
}

