package com.example.arcgisapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.arcgisapp.controller.MapController
import com.example.arcgisapp.view.MapActivityView

class MainActivity : AppCompatActivity() {

    private lateinit var mapActivityView: MapActivityView
    private lateinit var mapController: MapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapActivityView = MapActivityView(this)
        mapController = MapController(mapActivityView)

        mapController.onCreate(savedInstanceState)
    }

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
}