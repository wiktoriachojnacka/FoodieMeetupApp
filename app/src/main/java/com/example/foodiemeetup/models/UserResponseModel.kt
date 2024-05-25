package com.example.foodiemeetup.models

data class UserResponseModel(
    val username: String,
    val email: String,
    val emailConfirmed: Boolean,
    val age: Int,
    val gender: String,
    val jpgUrl: String,
    val active: Boolean){

    constructor() : this("-", "-", false, 0, "", "", false)
}
