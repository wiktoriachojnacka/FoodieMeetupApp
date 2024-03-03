package com.example.foodiemeetup.screens

import android.widget.Toast
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
import com.example.foodiemeetup.ViewModels.LoginViewModel
import com.example.foodiemeetup.models.RegisterModel
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.CheckboxComponent
import com.example.foodiemeetup.components.ClickableLoginTextComponent
import com.example.foodiemeetup.components.DividerTextComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.MyTextFieldComponent
import com.example.foodiemeetup.components.PasswordTextFieldComponent
import com.example.foodiemeetup.components.TextComponent
import com.example.foodiemeetup.navigation.FoodieMeetUpRouter
import com.example.foodiemeetup.navigation.Screen


@Composable
fun SignUpScreen(viewModel: LoginViewModel) {
Surface (
    color = Color.White,
    modifier = Modifier
        .fillMaxSize()
        .padding(28.dp)
        .background(Color.White)
    ){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val user = RegisterModel(username = username,email = email,password = password)
    var isChecked by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()){
        TextComponent(value = stringResource(id = R.string.hello))
        HeadingTextComponent(value = stringResource(id = R.string.create_account))
        Spacer(modifier = Modifier.height(20.dp))
        MyTextFieldComponent(labelValue = stringResource(id = R.string.first_name),painterResource(id = R.drawable.profile),helperValue= username,onhelperValueChange = { username = it })
        MyTextFieldComponent(labelValue = stringResource(id = R.string.email),painterResource(id = R.drawable.message),helperValue= email,onhelperValueChange = { email = it })
        PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password),painterResource(id = R.drawable.ic_lock), helperValue= password,onhelperValueChange = { password = it })
        CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions), onTextSelected = {
            FoodieMeetUpRouter.navigateTo(Screen.TermsAndConditionsScreen)},  onCheckedChange = {
            isChecked = it
        } )

        Spacer(modifier = Modifier.height(40.dp))

        ButtonComponent(value = stringResource(id = R.string.register) , onButtonClicked =
        {
            if (isChecked){
                viewModel.registerUser(user,context)
            } else {
                Toast.makeText(context, "You must give your consent for Privacy Policy and Term od Use", Toast.LENGTH_SHORT).show()
            }

        }, isEnabled = true)
        DividerTextComponent()
        ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
            FoodieMeetUpRouter.navigateTo(Screen.LoginScreen)
    })

    }
   }
}