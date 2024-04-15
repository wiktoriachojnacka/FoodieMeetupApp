package com.example.foodiemeetup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint

@Composable
fun HomeScreen() {
    val context = LocalContext.current

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

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen()
}