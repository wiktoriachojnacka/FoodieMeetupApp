package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.PreferencesScreenViewModel
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.PreferenceGenderRadioButtons
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.models.PreferencesResponseModel
import com.example.foodiemeetup.ui.theme.BgColor
import com.example.foodiemeetup.ui.theme.Primary
import com.example.foodiemeetup.ui.theme.Purple40

@Composable
fun PreferencesScreen(viewModel: PreferencesScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    var preferences: PreferencesResponseModel by remember { mutableStateOf(PreferencesResponseModel()) }
    val isLoading = viewModel.isLoading
    LaunchedEffect(Unit) {
        viewModel.getUserPreferences(token, context) { pref ->  preferences = pref }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        var prefGender by remember { mutableStateOf(preferences.gender) }
        var minAge by remember { mutableStateOf(preferences.minAge) }
        var maxAge by remember { mutableStateOf(preferences.maxAge) }
        var sliderPosition: ClosedFloatingPointRange<Float> by remember {
            mutableStateOf(preferences.minAge.toFloat()..preferences.maxAge.toFloat())
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
            prefGender = PreferenceGenderRadioButtons("${preferences.gender}")
            Spacer(modifier = Modifier.height(20.dp))
            TextToLeftComponent(20, "Age range")
            Spacer(modifier = Modifier.height(20.dp))
            RangeSlider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                onValueChangeFinished = {
                    minAge = sliderPosition.start.toInt()
                    maxAge = sliderPosition.endInclusive.toInt()
                },
                steps = 82,
                valueRange = 18F..100F,
                colors = SliderDefaults.colors(
                    thumbColor = Purple40,
                    activeTrackColor = Primary,
                    inactiveTrackColor = Color.Gray
                )
            )
            Text(minAge.toString() + " - " + maxAge.toString())
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(value = "Update Preferences", onButtonClicked = {
                viewModel.postDeletePreferences(token, context, preferences.preferenceId)
                viewModel.postCreatePreferences(token, context, maxAge, minAge, prefGender)
            }, isEnabled = true)

        }
    }
}
