package com.example.foodiemeetup.models

data class PreferencesResponseModel(
    val preferenceId: Int,
    val userName: String,
    val placeId: Int,
    val city: String,
    val placeType: String,
    val maxAge: Int,
    val minAge: Int,
    val gender: String,
    val timeOfDay: String) {

    constructor() : this(0,"-", 0,"Torun", "", 30, 18, "Both", "Afternoon")
}
