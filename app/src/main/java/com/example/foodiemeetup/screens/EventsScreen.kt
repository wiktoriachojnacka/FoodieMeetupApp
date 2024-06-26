package com.example.foodiemeetup.screens

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.NoFood
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.NoFood
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodiemeetup.ViewModels.EventsScreenViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.components.AwaitingItem
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.MatchedItem
import com.example.foodiemeetup.models.UserMatchesResponseModel
import com.example.foodiemeetup.ui.theme.BgColor
import com.example.foodiemeetup.ui.theme.Primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventsScreen(viewModel: EventsScreenViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }
    val myUsername by remember { mutableStateOf(appPreferences.getString("username")) }

    val date = SimpleDateFormat("dd.MM.yyyy")
    val time = SimpleDateFormat("HH:mm")

    var userMatches: List<UserMatchesResponseModel> by  remember {mutableStateOf(listOf()) }
    var userAwaiatingMatches: List<UserMatchesResponseModel> by  remember {mutableStateOf(listOf()) }

    viewModel.getUserMatches(token, context, "false") { uM ->  userMatches = uM }
    viewModel.getUserMatches(token, context, "true") { uM ->  userAwaiatingMatches = uM }

    val tabItems = listOf(
        TabItem(
            title = "Matched",
            unselectedIcon = Icons.Outlined.Fastfood,
            selectedIcon = Icons.Filled.Fastfood,

        ),
        TabItem(
            title = "Awaiting",
            unselectedIcon = Icons.Outlined.NoFood,
            selectedIcon = Icons.Filled.NoFood
        )
    )

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        HeadingTextComponent(value = "My Events")
        Spacer(modifier = Modifier.height(20.dp))
        TabRow(selectedTabIndex = selectedTabIndex,
            modifier = Modifier.border(2.dp, Primary, RectangleShape)
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(selected = index == selectedTabIndex,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                        selectedTabIndex = index
                    },
                    text = { Text(text = item.title)},
                    icon = {
                        Icon(imageVector = if(index == selectedTabIndex){
                            item.selectedIcon } else item.unselectedIcon,
                        contentDescription = item.title) },
                    modifier = Modifier.background(Color.White)
                    )

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        
        HorizontalPager(state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {index ->
            if(index == 0){
                var selected = false
                LazyColumn(modifier = Modifier.weight(1f)
                    .padding(start=30.dp, end=30.dp)) {
                    for (userMatch in userMatches) {
                        if(userMatch.matchedUser == myUsername){
                            var temp = userMatch.matchedUser
                            userMatch.matchedUser = userMatch.user
                            userMatch.user = temp
                            temp = userMatch.matchedUserGender
                            userMatch.matchedUserGender = userMatch.userGender
                            userMatch.userGender = temp
                        }
                        item {
                            MatchedItem(
                                matchedUser = userMatch.matchedUser,
                                placeName = userMatch.place.name,
                                placeAddress = userMatch.place.address,
                                date = date.format(userMatch.meetingTimestamp),
                                time = time.format(userMatch.meetingTimestamp),
                                gender = userMatch.matchedUserGender,
                                selected = selected,
                                onButtonClicked = {}
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
            else{
                var selMatch by remember { mutableStateOf(UserMatchesResponseModel()) }
                var selectedMatchId by remember { mutableStateOf(0) }

                LazyColumn(modifier = Modifier.weight(1f)
                    .padding(start=30.dp, end=30.dp)) {
                    for (userMatch in userAwaiatingMatches) {
                        var selected = false
                        if(selMatch == userMatch) {
                            selected = true
                            selectedMatchId = userMatch.matchId
                            viewModel.isDeleteButtonShown = true
                        }

                        item {
                            AwaitingItem(
                                placeName = userMatch.place.name,
                                placeAddress = userMatch.place.address,
                                date = date.format(userMatch.meetingTimestamp),
                                time = time.format(userMatch.meetingTimestamp),
                                selected = selected,
                                onButtonClicked = { selMatch = userMatch},
                                isEnabled = true
                            )
                            if (viewModel.isDeleteButtonShown && selectedMatchId == userMatch.matchId) {
                                Spacer(modifier = Modifier.height(25.dp))
                                ButtonComponent(value = "Delete event", onButtonClicked = {
                                    viewModel.postDeleteMatch(token, context, selectedMatchId)
                                    navController.navigate(route = "events")
                                }, isEnabled = true)
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }

        }
    }

}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)