package com.example.arcgisapp.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.example.arcgisapp.R
import com.example.arcgisapp.view.MapActivityView

class MapController (private val view: MapActivityView) {

    fun onCreate(savedInstanceState: Bundle?) {
        view.onCreate(savedInstanceState)
    }

    private fun setApiKeyForApp() {
        ArcGISRuntimeEnvironment.setApiKey("YOUR_API_KEY")
    }

    fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransition = view.activity.supportFragmentManager.beginTransaction()

        if (isTransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.add(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}