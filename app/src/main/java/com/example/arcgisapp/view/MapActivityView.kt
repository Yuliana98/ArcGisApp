package com.example.arcgisapp.view

import android.R
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.MapView
import com.example.arcgisapp.databinding.ActivityMainBinding


class MapActivityView(val activity: AppCompatActivity) {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(activity.layoutInflater)
    }

    private val mapView: MapView by lazy {
        activityMainBinding.mapView
    }

    val drawerLayout: DrawerLayout by lazy {
        activityMainBinding.drawerLayout
    }

    private val drawerList: ListView by lazy {
        activityMainBinding.drawerList
    }

    private lateinit var mNavigationDrawerItemTitles: Array<String>

    private val mDrawerToggle: ActionBarDrawerToggle by lazy {
        setupDrawer()
    }

    fun onCreate(savedInstanceState: Bundle?) {
        activity.setContentView(activityMainBinding.root)
        setApiKeyForApp()

        // Set up navigation drawer
        mNavigationDrawerItemTitles =
            BasemapStyle.values().map { it.name.replace("_", " ") }
                .toTypedArray()

        addDrawerItems()
        drawerLayout.addDrawerListener(mDrawerToggle)

        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = mNavigationDrawerItemTitles[0]
        }

        // Create a map with the imagery Basemap and set it to the map
        mapView.map = ArcGISMap(BasemapStyle.ARCGIS_IMAGERY)
        mapView.setViewpoint(Viewpoint(47.6047, -122.3334, 10000000.0))
    }

    private fun addDrawerItems() {
        ArrayAdapter(activity, R.layout.simple_list_item_1, mNavigationDrawerItemTitles).apply {
            drawerList.adapter = this
            drawerList.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ -> selectBasemap(position) }
        }
    }

    private fun setupDrawer() =
        object :
            ActionBarDrawerToggle(activity, drawerLayout, com.example.arcgisapp.R.string.drawer_open, com.example.arcgisapp.R.string.drawer_close) {

            override fun isDrawerIndicatorEnabled() = true

            /** Called when a drawer has settled in a completely open state.  */
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                activity.supportActionBar?.title = activity.title
                activity.invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state.  */
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                activity.invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }
        }

    /**
     * Select the Basemap item based on position in the navigation drawer
     *
     * @param position order int in navigation drawer
     */
    private fun selectBasemap(position: Int) {
        // update selected item and title, then close the drawer
        drawerList.setItemChecked(position, true)
        drawerLayout.closeDrawer(drawerList)

        // get basemap title by position
        val baseMapTitle = mNavigationDrawerItemTitles[position]
        activity.supportActionBar?.title = baseMapTitle

        // create a new Basemap(BasemapStyle.THE_ENUM_SELECTED)
        mapView.map.basemap =
            Basemap(BasemapStyle.valueOf(baseMapTitle.replace(" ", "_")))
    }

    private fun setApiKeyForApp() {

        ArcGISRuntimeEnvironment.setApiKey("AAPK349dbf4c9eab4be788cd463a2d0f6703vck-3kdRx2K2Z5RFURAA9-Izg6s0LL1xFZP6Rg7SBjHTh8mif16a7dxxdiQcqW4b")

    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Activate the navigation drawer toggle
        return mDrawerToggle.onOptionsItemSelected(item) || onOptionsItemSelected(item)

    }

    fun onPostCreate(savedInstanceState: Bundle?) {
        mDrawerToggle.syncState()
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        mDrawerToggle.onConfigurationChanged(newConfig)
    }

    fun onPause() {
        mapView.pause()
    }

    fun onResume() {
        mapView.resume()
    }

    fun onDestroy() {
        mapView.dispose()
    }

}