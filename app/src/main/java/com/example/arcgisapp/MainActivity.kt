package com.example.arcgisapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.PointCollection
import com.esri.arcgisruntime.geometry.Polygon
import com.esri.arcgisruntime.geometry.Polyline
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import com.example.arcgisapp.controller.MapController
import com.example.arcgisapp.databinding.ActivityMainBinding
import com.example.arcgisapp.view.MapActivityView

class MainActivity : AppCompatActivity() {

    private lateinit var mapActivityView: MapActivityView
    private lateinit var mapController: MapController

   /* private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mapView: MapView by lazy {
        activityMainBinding.mapView
    }


    private val drawerLayout: DrawerLayout by lazy {
        activityMainBinding.drawerLayout
    }

    private val drawerList: ListView by lazy {
        activityMainBinding.drawerList
    }

    private lateinit var mNavigationDrawerItemTitles: Array<String>

    private val mDrawerToggle: ActionBarDrawerToggle by lazy {
        setupDrawer()
    }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       mapActivityView = MapActivityView(this)
       mapController = MapController(mapActivityView)

       mapController.onCreate(savedInstanceState)
/*

//        setContentView(activityMainBinding.root)
//        addGraphics()

        setApiKeyForApp()

//        replaceFragment(MapFragment.newInstance(), true)

        // inflate navigation drawer with all basemap types in a human readable format
        mNavigationDrawerItemTitles =
            BasemapStyle.values().map { it.name.replace("_", " ") }
                .toTypedArray()

        addDrawerItems()
        drawerLayout.addDrawerListener(mDrawerToggle)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            // set opening basemap title to ARCGIS IMAGERY
            title = mNavigationDrawerItemTitles[0]
        }

        // create a map with the imagery Basemap and set it to the map
        mapActivityView.map = ArcGISMap(BasemapStyle.ARCGIS_IMAGERY)
        mapActivityView.setViewpoint(Viewpoint(47.6047, -122.3334, 10000000.0))
    */}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mapActivityView.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mapActivityView.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mapActivityView.onConfigurationChanged(newConfig)
    }

    override fun onPause() {
        super.onPause()
        mapActivityView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapActivityView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapActivityView.onDestroy()
    }

    /*fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransition = supportFragmentManager.beginTransaction()

        if (isTransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.add(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }*/
///////////////////////////
    /**
     * Add navigation drawer items
     *//*
    private fun addDrawerItems() {
        ArrayAdapter(this, android.R.layout.simple_list_item_1, mNavigationDrawerItemTitles).apply {
            drawerList.adapter = this
            drawerList.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ -> selectBasemap(position) }
        }
    }

    *//**
     * Set up the navigation drawer
     *//*
    private fun setupDrawer() =
        object :
            ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun isDrawerIndicatorEnabled() = true

            *//** Called when a drawer has settled in a completely open state.  *//*
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                supportActionBar?.title = title
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            *//** Called when a drawer has settled in a completely closed state.  *//*
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }
        }

    private fun addGraphics() {
        // create a graphics overlay and add it to the map view
        val graphicsOverlay = GraphicsOverlay()
        mapActivityView.graphicsOverlays.add(graphicsOverlay)


        *//**
         * Point
         *//*
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

        *//**
         * Line
         *//*
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

        *//**
         * Polygon
         *//*
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

    *//**
     * Select the Basemap item based on position in the navigation drawer
     *
     * @param position order int in navigation drawer
     *//*
    private fun selectBasemap(position: Int) {
        // update selected item and title, then close the drawer
        drawerList.setItemChecked(position, true)
        drawerLayout.closeDrawer(drawerList)

        // get basemap title by position
        val baseMapTitle = mNavigationDrawerItemTitles[position]
        supportActionBar?.title = baseMapTitle

        // create a new Basemap(BasemapStyle.THE_ENUM_SELECTED)
        mapActivityView.map.basemap =
            Basemap(BasemapStyle.valueOf(baseMapTitle.replace(" ", "_")))
    }
    //////////////////////////////////////

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Activate the navigation drawer toggle
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onPause() {
        super.onPause()
        mapActivityView.pause()
    }

    override fun onResume() {
        super.onResume()
        mapActivityView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapActivityView.dispose()
    }

    private fun setApiKeyForApp() {

        ArcGISRuntimeEnvironment.setApiKey("AAPK349dbf4c9eab4be788cd463a2d0f6703vck-3kdRx2K2Z5RFURAA9-Izg6s0LL1xFZP6Rg7SBjHTh8mif16a7dxxdiQcqW4b")

    }*/

}