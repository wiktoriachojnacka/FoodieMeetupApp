package com.example.foodiemeetup.authentication

import com.example.foodiemeetup.models.MapPointsResponseModel
import com.example.foodiemeetup.models.RegisterModel
import com.example.foodiemeetup.models.RegisterResponseModel
import com.example.foodiemeetup.models.StringResponseModel
import com.example.foodiemeetup.models.TokenResponseModel
import com.example.foodiemeetup.models.UserResponseModel
import retrofit2.Call

class LoginRepository {
    private val loginService= RetrofitInstance.loginService

    suspend fun loginUser(credentials: String): Call<TokenResponseModel> {
        return loginService.loginUser(credentials)
    }

    suspend fun createUser(user: RegisterModel): Call<RegisterResponseModel> {
        return loginService.createUsers(user)
    }

    suspend fun getUserData(token: String): Call<UserResponseModel> {
        return loginService.getUserData(token)
    }

    suspend fun postPasswordChange(token: String, credentials: String): Call<StringResponseModel> {
        return loginService.postPasswordChange(token, credentials)
    }

    suspend fun getMapPoints(): Call<List<MapPointsResponseModel>> {
        return loginService.getMapPoints()
    }
}