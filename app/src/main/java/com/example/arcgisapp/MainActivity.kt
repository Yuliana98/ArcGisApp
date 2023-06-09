package com.example.arcgisapp

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.PointCollection
import com.esri.arcgisruntime.geometry.Polygon
import com.esri.arcgisruntime.geometry.Polyline
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import com.example.arcgisapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mapView: MapView by lazy {
        activityMainBinding.mapView
    }
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(activityMainBinding.root)
        setupMap()
//        addGraphics()

        setApiKeyForApp()
    }

    private fun addGraphics() {
        // create a graphics overlay and add it to the map view
        val graphicsOverlay = GraphicsOverlay()
        mapView.graphicsOverlays.add(graphicsOverlay)


        /**
         * Point
         */
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(-118.8065, 34.0005, SpatialReferences.getWgs84())

        // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, -0xa8cd, 10f)

        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0xff9c01, 2f)
        simpleMarkerSymbol.outline = blueOutlineSymbol

        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(point, simpleMarkerSymbol)

        // add the point graphic to the graphics overlay
        graphicsOverlay.graphics.add(pointGraphic)

        /**
         * Line
         */
        // create a point collection with a spatial reference, and add three points to it
        val polylinePoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(-118.8215, 34.0139))
            add(Point(-118.8148, 34.0080))
            add(Point(-118.8088, 34.0016))
        }

        // create a polyline geometry from the point collection
        val polyline = Polyline(polylinePoints)

        // create a blue line symbol for the polyline
        val polylineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0xff9c01, 3f)

        // create a polyline graphic with the polyline geometry and symbol
        val polylineGraphic = Graphic(polyline, polylineSymbol)

        // add the polyline graphic to the graphics overlay
        graphicsOverlay.graphics.add(polylineGraphic)

        /**
         * Polygon
         */
        // create a point collection with a spatial reference, and add five points to it
        val polygonPoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(-118.8189, 34.0137))
            add(Point(-118.8067, 34.0215))
            add(Point(-118.7914, 34.0163))
            add(Point(-118.7959, 34.0085))
            add(Point(-118.8085, 34.0035))
        }
        // create a polygon geometry from the point collection
        val polygon = Polygon(polygonPoints)

        // create an orange fill symbol with 20% transparency and the blue simple line symbol
        val polygonFillSymbol =
            SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, -0x7f00a8cd, blueOutlineSymbol)

        // create a polygon graphic from the polygon geometry and symbol
        val polygonGraphic = Graphic(polygon, polygonFillSymbol)
        // add the polygon graphic to the graphics overlay
        graphicsOverlay.graphics.add(polygonGraphic)
    }


    private fun setupMap() {

        // create a map with the BasemapStyle streets
        val map = ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC)

        // set the map to be displayed in the layout's MapView
        mapView.map = map

        // set the viewpoint, Viewpoint(latitude, longitude, scale)
        mapView.setViewpoint(Viewpoint(34.0270, -118.8050, 72000.0))

    }

    override fun onPause() {
        super.onPause()
        mapView.pause()
    }

    override fun onResume() {
        super.onResume()
        mapView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.dispose()
    }

    private fun setApiKeyForApp(){
        // set your API key
        // Note: it is not best practice to store API keys in source code. The API key is referenced
        // here for the convenience of this tutorial.

        ArcGISRuntimeEnvironment.setApiKey("AAPK349dbf4c9eab4be788cd463a2d0f6703vck-3kdRx2K2Z5RFURAA9-Izg6s0LL1xFZP6Rg7SBjHTh8mif16a7dxxdiQcqW4b")

    }

}