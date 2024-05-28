package com.example.foodiemeetup.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.models.UserResponseModel
import com.example.foodiemeetup.ui.theme.BgColor


@Composable
fun SendUsAMessageScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    val user: UserResponseModel = UserResponseModel()
    var subject by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top = 28.dp, start = 28.dp, end = 28.dp)

    ) {
        HeadingTextComponent(value = "Send Us A Message")
        Spacer(modifier = Modifier.height(28.dp))
        TextToLeftComponent(size = 20, value = "Do You have a question?")
        TextToLeftComponent(size = 20, value = "Do You want to add new place for meeting?")
        Spacer(modifier = Modifier.height(5.dp))
        TextToLeftComponent(size = 25, value = "Write to us!")
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = subject,
            onValueChange = { subject = it },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            placeholder = { Text("Subject:") },
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .verticalScroll(rememberScrollState()),
            maxLines = 20,
            placeholder = { Text("Enter your text here:") },
        )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent(value = "Send", onButtonClicked = {
            navController.navigate(route = "Message")
            Toast.makeText(context, "Message has been sent!", Toast.LENGTH_SHORT).show()
        },isEnabled = true)
    }


}

@Composable
fun AboutUsScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top = 28.dp, start = 28.dp, end = 28.dp)

    ) {
        HeadingTextComponent(value = "About Us")
        Spacer(modifier = Modifier.height(28.dp))

    }


}

@Composable
fun FAQScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top = 28.dp, start = 28.dp, end = 28.dp)

    ) {
        HeadingTextComponent(value = "FAQ")
        Spacer(modifier = Modifier.height(28.dp))

        TextToLeftComponent(size = 20, value = "My friend created an event. Why can't I see it?")
        TextToLeftComponent(size = 15, value = "Maybe your friend don't match your preferences. " +
                "Or maybe it's the opposite and You don't fit in your friend's. Try to change it in Prefences Screen")
        Spacer(modifier = Modifier.height(15.dp))


    }


}
