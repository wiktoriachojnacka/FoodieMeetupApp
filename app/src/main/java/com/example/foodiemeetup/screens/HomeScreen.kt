package com.example.foodiemeetup.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.foodiemeetup.ViewModels.HomeScreenViewModel
import com.example.foodiemeetup.models.MapPointsResponseModel
import com.example.foodiemeetup.models.UserResponseModel
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