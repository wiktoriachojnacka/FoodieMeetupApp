package com.example.foodiemeetup.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.foodiemeetup.R
import com.example.foodiemeetup.ViewModels.HomeScreenViewModel
import com.example.foodiemeetup.models.MapPointsResponseModel
import com.example.foodiemeetup.models.UserResponseModel
import com.example.foodiemeetup.ui.theme.BgColor
import com.example.foodiemeetup.ui.theme.Primary
import com.example.foodiemeetup.ui.theme.Secondary
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    val context = LocalContext.current
    viewModel.getMapPoints(context)
    val points = viewModel.pointss  //Lista restauracji

    /*points.forEach{
        Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
    }*/
    Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", 0))

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Mapa
        OpenStreetMap(
           // modifier = Modifier
              //  .fillMaxWidth(),
              //  .weight(1f), // Używając wagi 1f, mapa będzie zajmować całą dostępną przestrzeń w kolumnie
            center = GeoPoint(53.0138, 18.5984) // Współrzędne dla Torunia
        )
        //viewModel.onPointClick() //Okno po kliknięciu w pinezke - szkic
    }

    if(viewModel.isDialogShown){
        ChosenPlaceDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                //viewmodel.dsd
            }
        )
    }
}


@Composable
fun OpenStreetMap(
   // modifier: Modifier = Modifier,
    center: GeoPoint
) {
    AndroidView(
      //  modifier = modifier,
        factory = { context ->
            val mapView = MapView(context)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.controller.setCenter(center)
            mapView.controller.setZoom(15.0)
            mapView
        }
    )
}

@Composable
fun ChosenPlaceDialog( onDismiss:() -> Unit, onConfirm:() -> Unit){
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
                .padding(15.dp, 40.dp, 15.dp, 100.dp)
                .border(2.dp, color = Secondary, shape = RoundedCornerShape(15.dp))
                .fillMaxSize()

            ) {
            Text(
                text = "Restauracja X",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal ),
                color = colorResource(id = R.color.colorText),
                modifier = Modifier.padding(30.dp, 30.dp, 30.dp, 0.dp)
            )
            Text(
                text = "Address",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal ),
                color = colorResource(id = R.color.colorText),
                modifier = Modifier.padding(30.dp, 5.dp)
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
                        .defaultMinSize(130.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Select",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

    }
}