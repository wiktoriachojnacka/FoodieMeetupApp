package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodiemeetup.R
import com.example.foodiemeetup.ViewModels.LoginViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.ProfileClickableItem
import com.example.foodiemeetup.components.TextComponent
import com.example.foodiemeetup.models.UserResponseModel
import com.example.foodiemeetup.navigation.FoodieMeetUpRouter
import com.example.foodiemeetup.navigation.Screen
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel) {

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
        HeadingTextComponent(value = "My Profile")
        Spacer(modifier = Modifier.height(28.dp))
        TextComponent(value = user.username + " age: " + user.age)
        Spacer(modifier = Modifier.height(28.dp))
        ButtonComponent(value = "Edit Profile", onButtonClicked = {

        })
        Spacer(modifier = Modifier.height(35.dp))

        LazyColumn (modifier = Modifier.fillMaxSize()){
            item {
                ProfileClickableItem(
                    value = "Notifications",
                    icon = ImageVector.vectorResource(R.drawable.settings_notifications),
                    iconTint = "#E5C85B",
                    onButtonClicked = { /*TODO*/ })
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "Preferences",
                    icon = ImageVector.vectorResource(R.drawable.settings_preferences),
                    iconTint = "#FFC58BF2",
                    onButtonClicked = { /*TODO*/ }
                )
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "Setting",
                    icon = ImageVector.vectorResource(R.drawable.settings_settings),
                    iconTint = "#6E6D69",
                    onButtonClicked = { /*TODO*/ })
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "Send us a message",
                    icon = ImageVector.vectorResource(R.drawable.settings_message),
                    iconTint = "#FF9DCEFF",
                    onButtonClicked = { /*TODO*/ })
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "About us",
                    icon = ImageVector.vectorResource(R.drawable.settings_about),
                    iconTint = "#98CC88",
                    onButtonClicked = { /*TODO*/ })

            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "FAQ",
                    icon = ImageVector.vectorResource(R.drawable.settings_faq),
                    iconTint = "#E0736F",
                    onButtonClicked = { /*TODO*/ })
            }

            item{
                Spacer(modifier = Modifier.height(35.dp))
            }
            item{
                ButtonComponent(value = "Logout", onButtonClicked = {
                    appPreferences.saveString("token", "0")

                        FoodieMeetUpRouter.navigateTo(Screen.LoginScreen)
                },isEnabled = true)
            }
            item{
                Spacer(modifier = Modifier.height(35.dp))
            }

        }

    }
}


