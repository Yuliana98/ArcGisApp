package com.example.arcgisapp.view

import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog.Builder
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.SpatialReference
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer
import com.esri.arcgisruntime.layers.LayerContent
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult
import com.example.arcgisapp.R
import kotlin.math.roundToInt


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
            ArcGISMapImageLayer("")
                /*.apply {
                addDoneLoadingListener {
                    // hide content
                    subLayerContents[0].isVisible = false
                }
            }*/


        baseMapView.mapView.map.operationalLayers.add(arcGISMapImageLayer)

        baseMapView.mapView.apply{
            // assign the map to the map view
            this.map = map

            // add a listener to detect taps on the map view
            onTouchListener = object : DefaultMapViewOnTouchListener(activity, this) {
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    e?.let {
                        val screenPoint = android.graphics.Point(
                            it.x.roundToInt(),
                            it.y.roundToInt()
                        )
                        identifyResult(screenPoint)
                    }
                    return true
                }
            }
        }


//        baseMapView.mapView.setViewpoint(Viewpoint(, , ))
    }

    /**
     * Performs an identify on layers at the given screenpoint and calls handleIdentifyResults(...) to process them.
     *
     * @param screenPoint in Android graphic coordinates.
     */
    private fun identifyResult(screenPoint: android.graphics.Point) {

        val identifyLayerResultsFuture = baseMapView.mapView
            .identifyLayersAsync(screenPoint, 12.0, false, 10)

        identifyLayerResultsFuture.addDoneListener {
            try {
                val identifyLayerResults = identifyLayerResultsFuture.get()
                handleIdentifyResults(identifyLayerResults)
            } catch (e: Exception) {
                println("Error identifying results ${e.message}")
            }
        }
    }

    /**
     * Processes identify results into a string which is passed to showAlertDialog(...).
     *
     * @param identifyLayerResults a list of identify results generated in identifyResult().
     */
    private fun handleIdentifyResults(identifyLayerResults: List<IdentifyLayerResult>) {
        val message = StringBuilder()
        var totalCount = 0
        for (identifyLayerResult in identifyLayerResults) {
            identifyLayerResult.sublayerResults.forEach {
                it.elements.forEach {
                    it.attributes.forEach {
                        message.append(it.key + " - " + it.value + "\n")
                    }
                }
            }
            val count = recursivelyCountIdentifyResultsForSublayers(identifyLayerResult)
            /*val layerName = identifyLayerResult.layerContent.name
            message.append(layerName).append(": ").append(count)*/

            // add new line character if not the final element in array
            if (identifyLayerResult != identifyLayerResults[identifyLayerResults.size - 1]) {
                message.append("\n")
            }
            totalCount += count
        }

        // if any elements were found show the results, else notify user that no elements were found
        if (totalCount > 0) {
            showAlertDialog(message)
        } else {
           println("No element found")
        }
    }

    /**
     * Gets a count of the GeoElements in the passed result layer.
     * This method recursively calls itself to descend into sublayers and count their results.
     * @param result from a single layer.
     * @return the total count of GeoElements.
     */
    private fun recursivelyCountIdentifyResultsForSublayers(result: IdentifyLayerResult): Int {
        var subLayerGeoElementCount = 0

        for (sublayerResult in result.sublayerResults) {
            // recursively call this function to accumulate elements from all sublayers
            subLayerGeoElementCount += recursivelyCountIdentifyResultsForSublayers(sublayerResult)
        }

        return subLayerGeoElementCount + result.elements.size
    }

    /**
     * Shows message in an AlertDialog.
     *
     * @param message contains identify results processed into a string.
     */
    private fun showAlertDialog(message: StringBuilder) {
        val alertDialogBuilder = Builder(activity)

        // set title
        alertDialogBuilder.setTitle("INFO")

        // set dialog message
        alertDialogBuilder
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, which -> }

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()

        // show the alert dialog
        alertDialog.show()
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