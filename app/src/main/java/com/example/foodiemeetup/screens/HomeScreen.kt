package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.foodiemeetup.R
import com.example.foodiemeetup.ViewModels.HomeScreenViewModel
import com.example.foodiemeetup.ViewModels.PreferencesManager
import com.example.foodiemeetup.components.BirthDateCalendarComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.models.CreateMatchModel
import com.example.foodiemeetup.ui.theme.BgColor
import com.example.foodiemeetup.ui.theme.Primary
import com.example.foodiemeetup.ui.theme.Secondary
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Marker.OnMarkerClickListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val appPreferences = remember { PreferencesManager.create(context) }
    val token by remember { mutableStateOf(appPreferences.getString("token")) }

    val isLoading = viewModel.isLoading
    LaunchedEffect(Unit) {
        viewModel.getMapPoints(context)
    }
    val points = viewModel.pointss  // Lista restauracji
    val center = GeoPoint(53.0104, 18.6050) //Starówka Toruń
    var pN by remember { mutableStateOf("")}
    var pA by remember { mutableStateOf("")}

    Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", 0))

        // Wyświetl ekran ładowania, jeśli dane są w trakcie ładowania
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            if(points.isEmpty()){
                LaunchedEffect(Unit) {
                    viewModel.getMapPoints(context)
                }
            }
        } else {
            Box (
                modifier = Modifier
                    .fillMaxSize()
            ){
                // Wyświetl mapę, gdy dane zostały pobrane
                AndroidView(
                    factory = { context ->
                        val mapView = MapView(context).apply {
                            setTileSource(TileSourceFactory.MAPNIK)
                            controller.setCenter(center)
                            controller.setZoom(18.0)
                        }

                        points.forEach { point ->
                            val marker = Marker(mapView,).apply {
                                position = GeoPoint(point.lat, point.lon)
                                //title = "V"
                                //snippet = point.address
                                setIcon(ContextCompat.getDrawable(context, R.drawable.map_marker))  //dodawanie wlasnej ikony

                            }
                            mapView.overlays.add(marker)
                            marker.setOnMarkerClickListener(OnMarkerClickListener { marker, mapView ->
                                //marker.showInfoWindow()
                                mapView.controller.animateTo(marker.position)
                                //viewModel.onPointClick(point.name, point.address)
                                pN = point.name
                                pA = point.address
                                true
                            })
                        }
                        mapView
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BgColor)
                        .align(Alignment.TopStart)

                ) {
                    Spacer(modifier = Modifier.height(28.dp))
                    HeadingTextComponent(value = "Map of restaurants & bars")
                    Spacer(modifier = Modifier.height(20.dp))
                }
                if(pA != "") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BgColor)
                            .align(Alignment.BottomStart)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Chosen point",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = colorResource(id = R.color.colorText),
                            modifier = Modifier.padding(30.dp, 0.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = pN,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = colorResource(id = R.color.colorText),
                            modifier = Modifier.padding(30.dp, 0.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = pA,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = colorResource(id = R.color.colorText),
                            modifier = Modifier.padding(30.dp, 0.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    viewModel.onCreateMatchClick()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Secondary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .defaultMinSize(170.dp),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "Create new event",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Button(
                                onClick = {
                                    val pp: String = pN
                                    navController.navigate(route = "Place/$pp")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Primary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .defaultMinSize(170.dp),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "Show events",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        //Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

    if(viewModel.isDialogShown){

        ChosenPlaceDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                    matchh -> val match: CreateMatchModel = matchh
                viewModel.postCreatematch(token, context, match)
                viewModel.onDismissDialog()
            },
            viewModel = viewModel,
            pN,
            pA
        )
    }
}

//Dialog do tworzenia nowego eventu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChosenPlaceDialog(onDismiss:() -> Unit, onConfirm: (CreateMatchModel) -> Unit, viewModel: HomeScreenViewModel, pointName: String, pointAddress: String){
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
                .border(2.dp, color = Secondary, shape = RoundedCornerShape(15.dp))
            ) {
            Column(
                modifier = Modifier.padding(15.dp,20.dp)
            ) {
                HeadingTextComponent(value = "New event")
                Spacer(modifier = Modifier.height(28.dp))
                TextToLeftComponent(20, pointName)
                Spacer(modifier = Modifier.height(5.dp))
                TextToLeftComponent(20, pointAddress)


                Spacer(modifier = Modifier.height(20.dp))
                TextToLeftComponent(20, "Pick date")
                Spacer(modifier = Modifier.height(10.dp))
                var date by rememberSaveable { mutableStateOf(Date().time) }
                BirthDateCalendarComponent() { endDatee -> date = endDatee }


                Spacer(modifier = Modifier.height(20.dp))
                TextToLeftComponent(20, "Set time")
                Spacer(modifier = Modifier.height(10.dp))
                val myState = rememberTimePickerState(9, 15, false)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TimeInput(state = myState,
                        modifier = Modifier.fillMaxWidth())
                    Text(text = "Time ${myState.hour} : ${myState.minute}",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ))
                }
                Spacer(modifier = Modifier.height(20.dp))

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
                            .defaultMinSize(100.dp),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Back",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            var match = CreateMatchModel(pointName, date.toFormattedString(), myState.hour, myState.minute)
                            onConfirm(match)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .defaultMinSize(100.dp),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Create",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

fun Long.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}