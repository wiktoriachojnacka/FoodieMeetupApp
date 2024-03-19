package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.models.UserResponseModel
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun SettingsScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    val user: UserResponseModel = viewModel.getUserData(token, context)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top=28.dp, start=28.dp, end=28.dp)

    ) {
        HeadingTextComponent(value = "Settings")
        Spacer(modifier = Modifier.height(28.dp))

    }


}

@Composable
fun SendUsAMessageScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    val user: UserResponseModel = viewModel.getUserData(token, context)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top=28.dp, start=28.dp, end=28.dp)

    ) {
        HeadingTextComponent(value = "Send Us A Message")
        Spacer(modifier = Modifier.height(28.dp))

    }


}

@Composable
fun AboutUsScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    val user: UserResponseModel = viewModel.getUserData(token, context)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top=28.dp, start=28.dp, end=28.dp)

    ) {
        HeadingTextComponent(value = "About Us")
        Spacer(modifier = Modifier.height(28.dp))

    }


}

@Composable
fun FAQScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    val user: UserResponseModel = viewModel.getUserData(token, context)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top=28.dp, start=28.dp, end=28.dp)

    ) {
        HeadingTextComponent(value = "FAQ")
        Spacer(modifier = Modifier.height(28.dp))

    }


}