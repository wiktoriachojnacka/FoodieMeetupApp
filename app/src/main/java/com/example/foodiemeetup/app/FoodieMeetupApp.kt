package com.example.foodiemeetup.app

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.foodiemeetup.ViewModels.LoginViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.navigation.FoodieMeetUpRouter
import com.example.foodiemeetup.navigation.Screen
import com.example.foodiemeetup.screens.LoginScreen
import com.example.foodiemeetup.screens.MainPage
import com.example.foodiemeetup.screens.SignUpScreen
import com.example.foodiemeetup.screens.TermsAndConditionsScreen


@Composable
    fun FoodieMeetupApp(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        if(token.equals("0")) {
            FoodieMeetUpRouter.navigateTo(Screen.SignUpScreen)
        }
        else {
            FoodieMeetUpRouter.navigateTo(Screen.MainPage)
        }

        Crossfade(targetState = FoodieMeetUpRouter.currentScreen, label = "") { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen(viewModel)

                }

                is Screen.TermsAndConditionsScreen -> {
                    TermsAndConditionsScreen()
                }

                is Screen.LoginScreen -> {
                    LoginScreen(viewModel)

                }
                is Screen.MainPage -> {
                    MainPage()
                }

            }
        }

    }
}