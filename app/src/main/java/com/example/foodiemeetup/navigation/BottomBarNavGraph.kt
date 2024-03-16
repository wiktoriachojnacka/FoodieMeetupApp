package com.example.foodiemeetup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.screens.BottomBarScreen
import com.example.foodiemeetup.screens.ChatScreen
import com.example.foodiemeetup.screens.EventsScreen
import com.example.foodiemeetup.screens.HomeScreen
import com.example.foodiemeetup.screens.ProfileScreen

@Composable
fun BottomBarNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route)
        {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Events.route)
        {
            EventsScreen()
        }
        composable(route = BottomBarScreen.Chat.route)
        {
            ChatScreen()
        }
        composable(route = BottomBarScreen.Profile.route)
        {
            ProfileScreen(ProfileScreenViewModel())
        }
    }
}