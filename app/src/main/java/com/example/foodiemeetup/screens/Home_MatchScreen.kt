package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.ViewModels.HomeMatchScreenViewModel
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun HomeMatchScreen(viewModel: HomeMatchScreenViewModel, navController: NavHostController) {
    val context = LocalContext.current

    val pointName = "pointName"
    val pointAddress = "pointAddress"

    //viewModel.getAvaiableMatches(context, pointName)
    //val availableMatches = viewModel.aMatches

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
        TextToLeftComponent(20, pointAddress)
    }
    LazyColumn(){

    }
}