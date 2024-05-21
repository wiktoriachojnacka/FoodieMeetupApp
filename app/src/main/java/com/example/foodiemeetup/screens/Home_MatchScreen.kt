package com.example.foodiemeetup.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.ViewModels.HomeMatchScreenViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun HomeMatchScreen(viewModel: HomeMatchScreenViewModel, navController: NavHostController, pointName: String) {
    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }

    val pointName = pointName
    viewModel.getAvailableMatches(token, context, pointName)
    val availableMatches = viewModel.aMatches

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top=28.dp, start=28.dp, end=28.dp)
    ) {
        HeadingTextComponent(value = "Available events")
        Spacer(modifier = Modifier.height(28.dp))
        TextToLeftComponent(20, pointName)
        Spacer(modifier = Modifier.height(20.dp))

    }
    LazyColumn(){

    }
    Toast.makeText(context, availableMatches.size.toString(), Toast.LENGTH_SHORT).show()
}