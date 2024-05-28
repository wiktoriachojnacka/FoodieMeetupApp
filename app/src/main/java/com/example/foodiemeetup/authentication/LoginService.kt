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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("register")
    fun createUsers(@Body usersinfo: RegisterModel): Call<RegisterResponseModel>

    @POST("token")
    fun loginUser(@Header("Authorization") credentials: String): Call<TokenResponseModel>

    @GET("user")
    fun getUserData(@Header("Authorization") token: String): Call<UserResponseModel>

    @POST("user/update")
    fun postUserUpdate(@Header("Authorization") token: String,
                       @Query(value="email", encoded=true) email: String,
                       @Query(value="gender", encoded=true) gender: String
    ): Call<StringResponseModel>

    @POST("user/delete")
    fun postUserDelete(@Header("Authorization") token: String): Call<StringResponseModel>

    @POST("user/password")
    fun postPasswordChange(@Header("Authorization") token: String, @Body password: PasswordModel): Call<StringResponseModel>

    @GET("getPreferences")
    fun getUserPreferences(@Header("Authorization") token: String): Call<PreferencesResponseModel>

    @POST("deletePreferences")
    fun postDeletePreferences(@Header("Authorization")token: String, @Query(value="preferencesId") preferencesId: Int): Call<StringResponseModel>

    @POST("createPreferences")
    fun postCreatePreferences(@Header("Authorization")token: String,
                              @Query(value="city", encoded=true) city: String,
                              @Query(value="placeType", encoded=true) placeType: String,
                              @Query(value="maxAge", encoded=true) maxAge: Int,
                              @Query(value="minAge", encoded=true) minAge: Int,
                              @Query(value="gender", encoded=true) gender: String,
                              @Query(value="timeOfDay", encoded=true) timeOfDay: String
    ): Call<StringResponseModel>


    @GET("places")
    fun getMapPoints(): Call<List<MapPointsResponseModel>>

    @GET("availablematches")
    fun getAvailableMatches(@Header("Authorization") token: String, @Query(value="placeName", encoded=true) placeName: String): Call<List<AvailableMatchesResponseModel>>

    @POST("creatematch")
    fun postCreateMatch(@Header("Authorization") token: String, @Body match: CreateMatchModel): Call<StringResponseModel>

    @POST("addusertomatch")
    fun postAddUserToMatch(@Header("Authorization") token: String, @Query(value="matchId") matchId: Int): Call<StringResponseModel>

    @GET("usermatches")
    fun getUserMatches(@Header("Authorization") token: String): Call<List<UserMatchesResponseModel>>

    @POST("match/delete")
    fun postDeleteMatch(@Header("Authorization") token: String, @Query(value="matchId") matchId: Int): Call<StringResponseModel>
}

