package com.example.foodiemeetup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.foodiemeetup.ViewModels.LoginViewModel
import com.example.foodiemeetup.app.FoodieMeetupApp
import com.example.foodiemeetup.screens.MainPage
import com.example.foodiemeetup.ui.theme.FoodieMeetUpTheme

class MainActivity : ComponentActivity() {
    private lateinit var myViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewModel = LoginViewModel()
        setContent {
            FoodieMeetupApp(loginViewModel=myViewModel)
        }
    }
}
