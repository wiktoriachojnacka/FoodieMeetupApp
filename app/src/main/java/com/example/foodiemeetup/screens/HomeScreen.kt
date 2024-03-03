package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun HomeScreen() {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ){
        Text(text = "Home Screen")
    }

}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen()
}