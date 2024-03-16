package com.example.foodiemeetup.models

data class UserResponseModel(
    val username: String,
    val email: String,
    val isActive: Boolean,
    val emailConfirmed: Boolean,
    val age: Int,
    val jpgUrl: String){

    constructor() : this("", "", false, false, 0, "")
}
