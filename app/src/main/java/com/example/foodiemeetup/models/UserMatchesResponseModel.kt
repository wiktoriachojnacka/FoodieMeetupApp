package com.example.foodiemeetup.models

import java.util.Date

data class UserMatchesResponseModel(
    val matchId: Int,
    val user: String,
    val matchedUser: String,
    val meetingTimestamp: Date,
    val place: MapPointsResponseModel
){
    constructor() : this(0,"", "", Date(),MapPointsResponseModel())
}
