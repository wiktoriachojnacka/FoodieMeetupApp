package com.example.foodiemeetup.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodiemeetup.R
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.ViewModels.ProfileScreenViewModel
import com.example.foodiemeetup.components.BirthDateCalendarComponent
import com.example.foodiemeetup.components.ButtonComponent
import com.example.foodiemeetup.components.GenderRadioButtons
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.MyTextFieldComponent
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.models.UserResponseModel
import com.example.foodiemeetup.ui.theme.BgColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditProfileScreen(viewModel: ProfileScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token = appPreferences.getString("token","")

    var user: UserResponseModel by remember { mutableStateOf(UserResponseModel()) }
    viewModel.getUserData(token, context) { userr ->  user = userr }

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    var endDate by rememberSaveable { mutableStateOf(Date().time) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top = 28.dp, start = 28.dp, end = 28.dp)

    ) {
        HeadingTextComponent(value = "Edit Profile")
        Spacer(modifier = Modifier.height(28.dp))
        TextToLeftComponent(20, "Username")
        MyTextFieldComponent(
            labelValue = user.username,
            painterResource(id = R.drawable.profile),
            helperValue= username,
            onhelperValueChange = { username = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextToLeftComponent(20, "Email")
        MyTextFieldComponent(
            labelValue = user.email,
            painterResource(id = R.drawable.message),
            helperValue= email,
            onhelperValueChange = { email = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextToLeftComponent(20, "Date of birth")
        BirthDateCalendarComponent() {endDatee -> endDate = endDatee}
        Spacer(modifier = Modifier.height(20.dp))
        TextToLeftComponent(20, "Gender")
        gender = GenderRadioButtons("Female")
        Spacer(modifier = Modifier.height(24.dp))
        ButtonComponent(value = "Update Info", onButtonClicked = {
            navController.navigate(route = "Edit")
        },isEnabled = true)

    }
    //Toast.makeText(context, endDate.toFormattedString(), Toast.LENGTH_SHORT).show()
}

fun Long.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

@Composable
@Preview
fun EditProfileScreenPreview() {
    EditProfileScreen(ProfileScreenViewModel(), navController = rememberNavController())
}