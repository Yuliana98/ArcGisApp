package com.example.arcgisapp.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle


class MapActivityView(private val activity: AppCompatActivity) {

    private val baseMapView: BasemapView = BasemapView(activity)



    fun onCreate(savedInstanceState: Bundle?) {
        activity.setContentView(baseMapView.activityMainBinding.root)
        setApiKeyForApp()

        // Set up navigation drawer
        baseMapView.mNavigationDrawerItemTitles =
            BasemapStyle.values().map { it.name.replace("_", " ") }
                .toTypedArray()

        baseMapView.addDrawerItems()
        baseMapView.drawerLayout.addDrawerListener(baseMapView.mDrawerToggle)

        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = baseMapView.mNavigationDrawerItemTitles[0]
        }

        // Create a map with the imagery Basemap and set it to the map
        baseMapView.mapView.map = ArcGISMap(BasemapStyle.ARCGIS_IMAGERY)

        // create the service arcGISMapImageLayer
        val arcGISMapImageLayer =
            ArcGISMapImageLayer("http://192.168.1.18:6080/arcgis/rest/services/Servis_SP4_test/MapServer")

        baseMapView.mapView.map.operationalLayers.add(arcGISMapImageLayer)

//        baseMapView.mapView.setViewpoint(Viewpoint(, , ))
    }

    private fun setApiKeyForApp() {

        ArcGISRuntimeEnvironment.setApiKey("AAPK349dbf4c9eab4be788cd463a2d0f6703vck-3kdRx2K2Z5RFURAA9-Izg6s0LL1xFZP6Rg7SBjHTh8mif16a7dxxdiQcqW4b")

    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Activate the navigation drawer toggle
        return baseMapView.mDrawerToggle.onOptionsItemSelected(item) || onOptionsItemSelected(item)

    }

    fun onPostCreate(savedInstanceState: Bundle?) {
        baseMapView.mDrawerToggle.syncState()
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        baseMapView.mDrawerToggle.onConfigurationChanged(newConfig)
    }

    fun onPause() {
        baseMapView.mapView.pause()
    }

    fun onResume() {
        baseMapView.mapView.resume()
    }

    fun onDestroy() {
        baseMapView.mapView.dispose()
    }
}