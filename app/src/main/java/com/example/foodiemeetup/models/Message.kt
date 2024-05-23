package com.example.foodiemeetup.models

 data class Message(
        val text: String,
        val author: String, //id autora widomosci
    ) {
     fun isFromMe(currentUsername: String): Boolean {
         return author == currentUsername
     }
 }
