package com.example.arcgisapp.view

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.view.MapView
import com.example.arcgisapp.databinding.ActivityMainBinding

class BasemapView(val activity: AppCompatActivity) {

    val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(activity.layoutInflater)
    }

    val mapView: MapView by lazy {
        activityMainBinding.mapView
    }

    val drawerLayout: DrawerLayout by lazy {
        activityMainBinding.drawerLayout
    }

    private val drawerList: ListView by lazy {
        activityMainBinding.drawerList
    }

    lateinit var mNavigationDrawerItemTitles: Array<String>

    val mDrawerToggle: ActionBarDrawerToggle by lazy {
        setupDrawer()
    }

    /**
     * Add navigation drawer items
     */
    fun addDrawerItems() {
        ArrayAdapter(activity, R.layout.simple_list_item_1, mNavigationDrawerItemTitles).apply {
            drawerList.adapter = this
            drawerList.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ -> selectBasemap(position) }
        }
    }

    /**
     * Set up the navigation drawer
     */
    private fun setupDrawer() =
        object :
            ActionBarDrawerToggle(
                activity,
                drawerLayout,
                com.example.arcgisapp.R.string.drawer_open,
                com.example.arcgisapp.R.string.drawer_close
            ) {

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
}