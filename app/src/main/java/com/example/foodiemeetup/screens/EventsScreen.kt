package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.ViewModels.EventsScreenViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun EventsScreen(viewModel: EventsScreenViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }

    if (viewModel.isLoading) {
        viewModel.getUserMatches(token, context)

        //Box(
        //    modifier = Modifier.fillMaxSize(),
        //    contentAlignment = Alignment.Center
        //) {
        //CircularProgressIndicator()
        // }
    } else {
        val userMatches = viewModel.userMatches
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BgColor)
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            HeadingTextComponent(value = "My Events")
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(modifier = Modifier.weight(1f)) {

                for (userMatch in userMatches) {
                    item {
                        TextToLeftComponent(15, value = "User: " + userMatch.user)
                        TextToLeftComponent(15, value = "Matched user: " + userMatch.matchedUser)
                        TextToLeftComponent(15, value = "Place: " +userMatch.place.name)
                        TextToLeftComponent(15, value = "Address: " +userMatch.place.address)
                        //TextToLeftComponent(15, value = "Time: " + userMatch.meetingTimestamp.toString())
                    }
                    item {
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
    }

}