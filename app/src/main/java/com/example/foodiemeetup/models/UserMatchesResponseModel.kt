package com.example.foodiemeetup.models

import java.util.Date

data class UserMatchesResponseModel(
    val matchId: Int,
    var user: String,
    var userGender: String,
    var matchedUser: String,
    var matchedUserGender: String,
    val meetingTimestamp: Date,
    val place: MapPointsResponseModel
){
    constructor() : this(0,"", "","","", Date(),MapPointsResponseModel())
}
