package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.ViewModels.EventsScreenViewModel
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun EventsScreen(viewModel: EventsScreenViewModel, navController: NavHostController) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ){
        Spacer(modifier = Modifier.height(28.dp))
        HeadingTextComponent(value = "My Events")
        Spacer(modifier = Modifier.height(20.dp))
    }



}