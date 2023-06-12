package com.example.arcgisapp.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.example.arcgisapp.R
import com.example.arcgisapp.view.MapActivityView

class MapController(private val view: MapActivityView) {

    fun onCreate(savedInstanceState: Bundle?) {
        view.onCreate(savedInstanceState)
    }

    /*private fun setApiKeyForApp() {
        ArcGISRuntimeEnvironment.setApiKey(com.example.arcgisapp.R.string.api_key.toString())
    }*/
}