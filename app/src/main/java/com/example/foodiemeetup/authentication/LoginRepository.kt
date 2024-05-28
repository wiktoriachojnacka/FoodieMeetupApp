package com.example.foodiemeetup.authentication

import com.example.foodiemeetup.models.AvailableMatchesResponseModel
import com.example.foodiemeetup.models.CreateMatchModel
import com.example.foodiemeetup.models.MapPointsResponseModel
import com.example.foodiemeetup.models.PasswordModel
import com.example.foodiemeetup.models.PreferencesResponseModel
import com.example.foodiemeetup.models.RegisterModel
import com.example.foodiemeetup.models.RegisterResponseModel
import com.example.foodiemeetup.models.StringResponseModel
import com.example.foodiemeetup.models.TokenResponseModel
import com.example.foodiemeetup.models.UserMatchesResponseModel
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

    suspend fun postUserUpdate(token: String, email: String, gender: String): Call<StringResponseModel>{
        return loginService.postUserUpdate(token, email, gender)
    }

    suspend fun postUserDelete(token: String): Call<StringResponseModel>{
        return loginService.postUserDelete(token)
    }

    suspend fun postPasswordChange(token: String, password: PasswordModel): Call<StringResponseModel> {
        return loginService.postPasswordChange(token, password)
    }

    fun getUserPreferences(token: String): Call<PreferencesResponseModel>{
        return loginService.getUserPreferences(token)
    }

    fun postDeletePreferences(token: String, preferencesId: Int): Call<StringResponseModel>{
        return loginService.postDeletePreferences(token, preferencesId)
    }

    fun postCreatePreferences(token: String, city: String, placeType: String, maxAge: Int, minAge: Int, gender: String, timeOfDay: String): Call<StringResponseModel> {
        return loginService.postCreatePreferences(token, city, placeType, maxAge, minAge, gender, timeOfDay)
    }

    suspend fun getMapPoints(): Call<List<MapPointsResponseModel>> {
        return loginService.getMapPoints()
    }

    suspend fun getAvailableMatches(token: String, takePreferencesIntoAccount: String, placeName: String): Call<List<AvailableMatchesResponseModel>> {
        return loginService.getAvailableMatches(token, takePreferencesIntoAccount, placeName)
    }

    suspend fun postCreateMatch(token: String, match: CreateMatchModel):Call<StringResponseModel>{
        return loginService.postCreateMatch(token, match)
    }

    fun postAddUserToMatch(token: String, matchId: Int): Call<StringResponseModel>{
        return loginService.postAddUserToMatch(token, matchId)
    }

    fun getUserMatches(token: String, openMatches: String): Call<List<UserMatchesResponseModel>>{
        return loginService.getUserMatches(token, openMatches)
    }

    fun postDeleteMatch(token: String, matchId: Int): Call<StringResponseModel>{
        return loginService.postDeleteMatch(token, matchId)
    }
}