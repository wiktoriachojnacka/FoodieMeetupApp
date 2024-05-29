package com.example.foodiemeetup.models

import com.google.gson.annotations.SerializedName
import java.util.Date


data class Chat(
    @SerializedName("chatId") val chatId: Int,
    @SerializedName("created") val created: Date
)

data class AvailableChat(
    @SerializedName("chat") val chat: Chat,
    @SerializedName("username") val username: String
)
