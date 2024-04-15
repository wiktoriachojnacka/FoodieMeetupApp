package com.example.foodiemeetup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.foodiemeetup.ViewModels.PasswordChangeScreenViewModel
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.screens.AboutUsScreen
import com.example.foodiemeetup.models.BottomBarScreen
import com.example.foodiemeetup.screens.ChangePasswordScreen
import com.example.foodiemeetup.screens.ChatScreen
import com.example.foodiemeetup.screens.EditProfileScreen
import com.example.foodiemeetup.screens.EventsScreen
import com.example.foodiemeetup.screens.FAQScreen
import com.example.foodiemeetup.screens.HomeScreen
import com.example.foodiemeetup.screens.PreferencesScreen
import com.example.foodiemeetup.screens.ProfileScreen
import com.example.foodiemeetup.screens.SendUsAMessageScreen

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
        navigation(
            startDestination = "Profile",
            route = BottomBarScreen.Profile.route
        ){
            composable(route = "Profile")
            {
                ProfileScreen(ProfileScreenViewModel(), navController = navController)
            }
            composable(route = "Edit")
            {
                EditProfileScreen(ProfileScreenViewModel(), navController = navController)
            }
            composable(route = "Preferences")
            {
                PreferencesScreen(ProfileScreenViewModel(), navController = navController)
            }
            composable(route = "ChangePassword")
            {
                ChangePasswordScreen(PasswordChangeScreenViewModel(), navController = navController)
            }
            composable(route = "Message")
            {
                SendUsAMessageScreen(ProfileScreenViewModel(), navController = navController)
            }
            composable(route = "About")
            {
                AboutUsScreen(ProfileScreenViewModel(), navController = navController)
            }
            composable(route = "FAQ")
            {
                FAQScreen(ProfileScreenViewModel(), navController = navController)
            }
        }

    }
}
