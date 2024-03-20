package com.example.foodiemeetup.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodiemeetup.R
import com.example.foodiemeetup.ViewModels.LoginViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.ProfileClickableItem
import com.example.foodiemeetup.components.TextComponent
import com.example.foodiemeetup.models.UserResponseModel
import com.example.foodiemeetup.navigation.BottomBarNavGraph
import com.example.foodiemeetup.navigation.FoodieMeetUpRouter
import com.example.foodiemeetup.navigation.Screen
import com.example.foodiemeetup.navigation.SystemBackButtonHandler
import com.example.foodiemeetup.ui.theme.AccentColor
import com.example.foodiemeetup.ui.theme.BgColor
import com.example.foodiemeetup.ui.theme.Primary
import com.example.foodiemeetup.ui.theme.Secondary

@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }

    var user: UserResponseModel by remember { mutableStateOf(UserResponseModel()) }
    viewModel.getUserData(token, context) { userr ->  user = userr }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top = 28.dp, start = 28.dp, end = 28.dp)

    ) {
        HeadingTextComponent(value = "My Profile")
        Spacer(modifier = Modifier.height(28.dp))
        TextComponent(value = user.username + " age: " + user.age)
        Spacer(modifier = Modifier.height(28.dp))
        ButtonComponent(value = "Edit Profile Info", onButtonClicked = {
            navController.navigate(route = "Edit")
        },isEnabled = true)
        Spacer(modifier = Modifier.height(35.dp))

        LazyColumn (modifier = Modifier.fillMaxSize()){
            item {
                ProfileClickableItem(
                    value = "Notifications",
                    icon = ImageVector.vectorResource(R.drawable.settings_notifications),
                    iconTint = "#E5C85B",
                    onButtonClicked = { /*TODO*/ })
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "Preferences",
                    icon = ImageVector.vectorResource(R.drawable.settings_preferences),
                    iconTint = "#FFC58BF2",
                    onButtonClicked = {
                        navController.navigate(route = "Preferences")
                    }, isEnabled = true)
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "Change password",
                    icon = ImageVector.vectorResource(R.drawable.settings_change_password),
                    iconTint = "#6E6D69",
                    onButtonClicked = {
                        navController.navigate(route = "ChangePassword")
                    }, isEnabled = true)
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "Delete account",
                    icon = ImageVector.vectorResource(R.drawable.settings_deleteaccount),
                    iconTint = "#E0736F",
                    onButtonClicked = {
                        viewModel.onDeleteUserClick()
                    }, isEnabled = true)
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "Send us a message",
                    icon = ImageVector.vectorResource(R.drawable.settings_message),
                    iconTint = "#FF9DCEFF",
                    onButtonClicked = {
                        navController.navigate(route = "Message")
                    }, isEnabled = true)
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "About us",
                    icon = ImageVector.vectorResource(R.drawable.settings_about),
                    iconTint = "#98CC88",
                    onButtonClicked = {
                        navController.navigate(route = "About")
                    }, isEnabled = true)

            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            item {
                ProfileClickableItem(
                    value = "FAQ",
                    icon = ImageVector.vectorResource(R.drawable.settings_faq),
                    iconTint = "#DC9259",
                    onButtonClicked = {
                        navController.navigate(route = "FAQ")
                    }, isEnabled = true)
            }

            item{
                Spacer(modifier = Modifier.height(35.dp))
            }
            item{
                ButtonComponent(value = "Logout", onButtonClicked = {
                    appPreferences.saveString("token", "0")

                    FoodieMeetUpRouter.navigateTo(Screen.LoginScreen)
                },isEnabled = true)
            }
            item{
                Spacer(modifier = Modifier.height(35.dp))
            }

        }

    }

    if(viewModel.isDialogShown){
        DeleteUserDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                //viewmodel.buyItem()
            }
        )
    }
}

@Composable
fun DeleteUserDialog( onDismiss:() -> Unit, onConfirm:() -> Unit){
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ){
        Card(
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = BgColor,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .border(2.dp, color = Secondary, shape = RoundedCornerShape(15.dp)),

        ) {
            Text(
                text = "Are you sure u want to DELETE your account?",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal ),
                color = colorResource(id = R.color.colorText),
                modifier = Modifier.padding(30.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Secondary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .defaultMinSize(130.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Cancel",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
                Button(
                    onClick = {
                        onConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .defaultMinSize(130.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Confirm",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

    }
}

/*@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(ProfileScreenViewModel(), navController = rememberNavController())
}*/

@Composable
@Preview
fun DialogPreview() {
    DeleteUserDialog(onDismiss = { /*TODO*/ }) {
        
    }
}

