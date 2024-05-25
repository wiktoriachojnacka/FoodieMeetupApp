package com.example.foodiemeetup.models

import com.example.foodiemeetup.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
){

    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.bottom_home,
        icon_focused = R.drawable.bottom_home_focused
    )

    object Events: BottomBarScreen(
        route = "events",
        title = "My Events",
        icon = R.drawable.bottom_events,
        icon_focused = R.drawable.bottom_events_focused
    )

    object Chat: BottomBarScreen(
        route = "chat",
        title = "Chat",
        icon = R.drawable.bottom_chat,
        icon_focused = R.drawable.bottom_chat_focused
    )

    object Profile: BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = R.drawable.bottom_profile,
        icon_focused = R.drawable.bottom_profile_focused
    )

}
