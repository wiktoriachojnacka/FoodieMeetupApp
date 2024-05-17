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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.foodiemeetup.R
import com.example.foodiemeetup.ViewModels.HomeScreenViewModel
import com.example.foodiemeetup.components.BirthDateCalendarComponent
import com.example.foodiemeetup.components.HeadingTextComponent
import com.example.foodiemeetup.components.TextToLeftComponent
import com.example.foodiemeetup.models.MapPointsResponseModel
import com.example.foodiemeetup.ui.theme.BgColor
import com.example.foodiemeetup.ui.theme.Primary
import com.example.foodiemeetup.ui.theme.Secondary
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Marker.OnMarkerClickListener
import java.util.Date

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val isLoading = viewModel.isLoading
    LaunchedEffect(Unit) {
        viewModel.getMapPoints(context)
    }
    val points = viewModel.pointss  // Lista restauracji
    val center = GeoPoint(53.0138, 18.5984)
    var pN by remember { mutableStateOf("")}
    var pA by remember { mutableStateOf("")}
  //  Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", 0))

        // Wyświetl ekran ładowania, jeśli dane są w trakcie ładowania
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box (
                modifier = Modifier
                    .fillMaxSize()
            ){
                // Wyświetl mapę, gdy dane zostały pobrane
                //OpenStreetMap(
                //    center = GeoPoint(53.0138, 18.5984), // Współrzędne dla Torunia
                //    points = points,
                //    viewModell = viewModel
                //)
                AndroidView(
                    factory = { context ->
                        val mapView = MapView(context).apply {
                            setTileSource(TileSourceFactory.MAPNIK)
                            controller.setCenter(center)
                            controller.setZoom(16.0)
                        }

                        points.forEach { point ->
                            val marker = Marker(mapView).apply {
                                position = GeoPoint(point.lat, point.lon)
                                title = point.name
                                snippet = point.address
                                setIcon(ContextCompat.getDrawable(context, R.drawable.map_marker))  //dodawanie wlasnej ikony

                            }
                            mapView.overlays.add(marker)
                            marker.setOnMarkerClickListener(OnMarkerClickListener { marker, mapView ->
                                marker.showInfoWindow()
                                mapView.controller.animateTo(marker.position)
                                viewModel.onPointClick(point.name, point.address)
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
                        Text(
                            text = "Chosen point",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = colorResource(id = R.color.colorText),
                            modifier = Modifier.padding(30.dp, 10.dp, 30.dp, 0.dp)
                        )
                        Text(
                            text = pN,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = colorResource(id = R.color.colorText),
                            modifier = Modifier.padding(30.dp, 10.dp, 30.dp, 0.dp)
                        )
                        Text(
                            text = pA,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = colorResource(id = R.color.colorText),
                            modifier = Modifier.padding(30.dp, 5.dp, 30.dp, 10.dp)
                        )

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
                                    navController.navigate(route = "Place")
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
                        Spacer(modifier = Modifier.height(10.dp))
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
                viewModel.onConfirmDialog()
            },
            viewModel.pointName,
            viewModel.pointAddress
        )
    }
}

@Composable
fun OpenStreetMap(
    center: GeoPoint,
    points: List<MapPointsResponseModel>,
    viewModell: HomeScreenViewModel
) {
    AndroidView(
        factory = { context ->
            val mapView = MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                controller.setCenter(center)
                controller.setZoom(16.0)
            }

            points.forEach { point ->
                val marker = Marker(mapView).apply {
                    position = GeoPoint(point.lat, point.lon)
                    title = point.name
                    snippet = point.address
                    setIcon(ContextCompat.getDrawable(context, R.drawable.map_marker))  //dodawanie wlasnej ikony

                }
                mapView.overlays.add(marker)
                marker.setOnMarkerClickListener(OnMarkerClickListener { marker, mapView ->
                    marker.showInfoWindow()
                    mapView.controller.animateTo(marker.position)
                    viewModell.onPointClick(point.name, point.address)
                    true
                })
            }


            mapView
        }
    )
}


//Dialog do tworzenia nowego eventu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChosenPlaceDialog(onDismiss:() -> Unit, onConfirm:() -> Unit, pointName: String, pointAddress: String){
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
                //.padding(start=5.dp, end=5.dp)
                .border(2.dp, color = Secondary, shape = RoundedCornerShape(15.dp))
                //.fillMaxSize()

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
                var endDate by rememberSaveable { mutableStateOf(Date().time) }
                BirthDateCalendarComponent() { endDatee -> endDate = endDatee }


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
                            onConfirm()
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

@Composable
@Preview
fun ChosenDialogPreview() {
    ChosenPlaceDialog(
        onDismiss = { /*TODO*/ },
        onConfirm = { /*TODO*/ },
        pointName = "XD",
        pointAddress = "AA"
    )
}