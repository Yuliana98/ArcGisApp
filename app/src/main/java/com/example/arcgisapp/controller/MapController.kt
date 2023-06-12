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

    private fun setApiKeyForApp() {
        ArcGISRuntimeEnvironment.setApiKey("AAPK349dbf4c9eab4be788cd463a2d0f6703vck-3kdRx2K2Z5RFURAA9-Izg6s0LL1xFZP6Rg7SBjHTh8mif16a7dxxdiQcqW4b")
    }
}