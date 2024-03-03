package com.example.foodiemeetup.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodiemeetup.R
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.LoginViewModel
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.ClickableLoginTextComponent
import com.example.foodiemeetup.components.DividerTextComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.MyTextFieldComponent
import com.example.foodiemeetup.components.PasswordTextFieldComponent
import com.example.foodiemeetup.components.TextComponent
import com.example.foodiemeetup.components.UnderLinedTextComponent
import com.example.foodiemeetup.navigation.FoodieMeetUpRouter
import com.example.foodiemeetup.navigation.Screen
import com.example.foodiemeetup.navigation.SystemBackButtonHandler
import okhttp3.Credentials


@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
            .background(Color.White)
    ) {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val context = LocalContext.current
        val appPreferences = remember { PreferencesManager.create(context) }




        Column(modifier = Modifier.fillMaxSize()) {
            TextComponent(value = stringResource(id = R.string.login))
            HeadingTextComponent(value = stringResource(id = R.string.welcome_back))
            Spacer(modifier = Modifier.height(20.dp))
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource(id = R.drawable.message),
                helperValue = email,
                onhelperValueChange = { email = it })
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource(id = R.drawable.ic_lock),
                helperValue = password,
                onhelperValueChange = { password = it })
            UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password))
            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(value = stringResource(id = R.string.login), onButtonClicked = {
                val credentials: String = Credentials.basic(email, password)
                viewModel.loginUser(credentials, context) { token ->
                    appPreferences.saveString("token", token)
                    if (!token.equals("0"))
                    FoodieMeetUpRouter.navigateTo(Screen.MainPage)
                }
            }, isEnabled = true)

            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()
            ClickableLoginTextComponent(
                tryingToLogin = false,
                onTextSelected = { FoodieMeetUpRouter.navigateTo(Screen.SignUpScreen) })
            SystemBackButtonHandler { FoodieMeetUpRouter.navigateTo(Screen.SignUpScreen) }


        }

    }
}
