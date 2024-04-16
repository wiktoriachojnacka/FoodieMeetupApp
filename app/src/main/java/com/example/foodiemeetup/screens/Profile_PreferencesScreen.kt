package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.components.GenderRadioButtons
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.PreferenceGenderRadioButtons
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.models.UserResponseModel
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun PreferencesScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    var user: UserResponseModel by remember { mutableStateOf(UserResponseModel()) }
    viewModel.getUserData(token, context) { userr ->  user = userr }

    var prefGender by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top = 28.dp, start = 28.dp, end = 28.dp)

    ) {
        HeadingTextComponent(value = "Preferences")
        Spacer(modifier = Modifier.height(28.dp))
        TextToLeftComponent(20, "Gender")
        prefGender = PreferenceGenderRadioButtons("Male")
        Spacer(modifier = Modifier.height(20.dp))
        TextToLeftComponent(20, "Age range")
        Spacer(modifier = Modifier.height(20.dp))

    }


}

@Composable
@Preview
fun PreferencesScreenPreview() {
    PreferencesScreen(ProfileScreenViewModel(), navController = rememberNavController())
}