package com.example.foodiemeetup.models

data class UserMatchesResponseModel(
    val matchId: Int,
    val user: String,
    val matchedUser: String,
    //val meetingTimestamp: Timestamp,
    val place: MapPointsResponseModel
)
