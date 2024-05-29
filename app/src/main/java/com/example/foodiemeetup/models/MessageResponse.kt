package com.example.foodiemeetup.models

import java.time.LocalDateTime

data class MessageResponse(
    val username: String,
    val message: String,
    val timestamp: String
)
