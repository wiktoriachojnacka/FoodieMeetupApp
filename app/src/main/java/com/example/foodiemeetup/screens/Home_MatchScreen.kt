package com.example.foodiemeetup.screens

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.R
import com.example.foodiemeetup.ViewModels.HomeMatchScreenViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.components.AvailableMatchesItem
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.models.AvailableMatchesResponseModel
import com.example.foodiemeetup.ui.theme.BgColor

@Composable
fun HomeMatchScreen(viewModel: HomeMatchScreenViewModel, navController: NavHostController, pointName: String) {
    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }

    var selMatch by remember { mutableStateOf(AvailableMatchesResponseModel(0,"", "", "")) }

    val pointName = pointName
    val isLoading = viewModel.isLoading

    var selectedMatchId by remember { mutableStateOf(0) }

    if (isLoading) {
        viewModel.getAvailableMatches(token, context, pointName)

        //Box(
        //    modifier = Modifier.fillMaxSize(),
        //    contentAlignment = Alignment.Center
        //) {
            //CircularProgressIndicator()
       // }
    } else {
        val availableMatches = viewModel.aMatches

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BgColor)
                .padding(top = 28.dp, start = 28.dp, end = 28.dp)
        ) {
            HeadingTextComponent(value = "Available events")
            Spacer(modifier = Modifier.height(28.dp))
            TextToLeftComponent(20, pointName)
            Spacer(modifier = Modifier.height(10.dp))

            if (viewModel.aMatches.size != 0) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    for (availableMatch in availableMatches) {
                        var selected = false
                        if(selMatch == availableMatch){
                            selected = true
                            selectedMatchId = availableMatch.matchId
                            viewModel.isJoinButtonShown = true
                        }


                        item {
                            AvailableMatchesItem(
                                date = availableMatch.date,
                                time = availableMatch.time,
                                gender = availableMatch.gender,
                                selected = selected,
                                icon = ImageVector.vectorResource(R.drawable.match_icon),
                                iconTint = "#E5C85B",
                                onButtonClicked = { selMatch = availableMatch},
                                isEnabled = true
                            )
                            if (viewModel.isJoinButtonShown && selectedMatchId == availableMatch.matchId) {
                                Spacer(modifier = Modifier.height(25.dp))
                                ButtonComponent(value = "Join event", onButtonClicked = {
                                    viewModel.postAddUserToMatch(
                                        token = token,
                                        context = context,
                                        matchId = availableMatch.matchId
                                    )
                                    navController.navigate(route = "Place/$pointName")
                                }, isEnabled = true)
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(25.dp))
                        }
                    }
                }

            } else {
                TextToLeftComponent(20, "There are none events for this place yet.")
                TextToLeftComponent(20, "Create one!")
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

}