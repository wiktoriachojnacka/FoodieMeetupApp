package com.example.foodiemeetup.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.GenderRadioButtons
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.PreferenceGenderRadioButtons
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.models.UserResponseModel
import com.example.foodiemeetup.navigation.FoodieMeetUpRouter
import com.example.foodiemeetup.navigation.Screen
import com.example.foodiemeetup.ui.theme.AccentColor
import com.example.foodiemeetup.ui.theme.BgColor
import com.example.foodiemeetup.ui.theme.Primary
import com.example.foodiemeetup.ui.theme.Purple40

@Composable
fun PreferencesScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    var user: UserResponseModel by remember { mutableStateOf(UserResponseModel()) }
    viewModel.getUserData(token, context) { userr ->  user = userr }

    var prefGender by remember { mutableStateOf("") }
    var minAge by remember { mutableStateOf(18) }
    var maxAge by remember { mutableStateOf(118) }

    var sliderPosition: ClosedFloatingPointRange<Float> by remember {
        mutableStateOf( 18F..118F)
    }

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
        RangeSlider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it},
            onValueChangeFinished = {
                minAge = sliderPosition.start.toInt()
                maxAge = sliderPosition.endInclusive.toInt()
            },
            steps = 100,
            valueRange = 18F..118F,
            colors = SliderDefaults.colors(
                thumbColor = Purple40,
                activeTrackColor = Primary,
                inactiveTrackColor = Color.Gray
            )
        )
        Text(minAge.toString()+ " - " + maxAge.toString())
        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent(value = "Update Preferences", onButtonClicked = {

        },isEnabled = true)

    }


}



@Composable
@Preview
fun PreferencesScreenPreview() {
    PreferencesScreen(ProfileScreenViewModel(), navController = rememberNavController())
}