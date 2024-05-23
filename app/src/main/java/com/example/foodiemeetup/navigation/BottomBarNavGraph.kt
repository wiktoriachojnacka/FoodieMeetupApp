package com.example.foodiemeetup.navigation

import ChatViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

import com.example.foodiemeetup.ViewModels.HomeMatchScreenViewModel
import com.example.foodiemeetup.ViewModels.HomeScreenViewModel
import com.example.foodiemeetup.ViewModels.LoginViewModel
import com.example.foodiemeetup.ViewModels.PasswordChangeScreenViewModel
import com.example.foodiemeetup.ViewModels.PreferencesScreenViewModel
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.models.BottomBarScreen
import com.example.foodiemeetup.screens.AboutUsScreen
import com.example.foodiemeetup.screens.ChangePasswordScreen
import com.example.foodiemeetup.screens.ChatScreen
import com.example.foodiemeetup.screens.EditProfileScreen
import com.example.foodiemeetup.screens.EventsScreen
import com.example.foodiemeetup.screens.FAQScreen
import com.example.foodiemeetup.screens.HomeMatchScreen
import com.example.foodiemeetup.screens.HomeScreen
import com.example.foodiemeetup.screens.PreferencesScreen
import com.example.foodiemeetup.screens.ProfileScreen
import com.example.foodiemeetup.screens.SendUsAMessageScreen


@Composable
fun BottomBarNavGraph(
    navController: NavHostController
) {
    val chatViewModel: ChatViewModel = viewModel() // Inicjalizacja ChatViewModel
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        navigation(startDestination = "Home",
            route = BottomBarScreen.Home.route
        ){
            composable(route = "Home")
            {
                HomeScreen(HomeScreenViewModel(), navController = navController)
            }
            composable(route = "Place/{pointName}", arguments = listOf(
                navArgument("pointName") { type = NavType.StringType }
            ))
            {
                val pointName = it.arguments?.getString("pointName") ?: ""
                HomeMatchScreen(HomeMatchScreenViewModel(), navController = navController, pointName)
            }
        }
        composable(route = BottomBarScreen.Events.route)
        {
            EventsScreen()
        }
        composable(route = BottomBarScreen.Chat.route) {
            // Użyj ChatScreen, przekazując chatViewModel jako argument
            ChatScreen(viewModel = chatViewModel)
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
                PreferencesScreen(PreferencesScreenViewModel(), navController = navController)
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
