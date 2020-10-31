package com.kotlin.openweatherapp.ui.activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.cachedb.ChosenLocation
import com.kotlin.openweatherapp.ui.viewmodels.MainViewModel
import com.kotlin.openweatherapp.ui.adapters.ChosenLocationsAdapter
import com.kotlin.openweatherapp.util.DeviceLocationHelper
import com.kotlin.openweatherapp.util.LOCATION_TYPE
import com.kotlin.openweatherapp.util.LOCATION_TYPE_CURRENT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_locations.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


private val fields =
    listOf(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ID)

private const val AUTOCOMPLETE_REQUEST_CODE = 1

@AndroidEntryPoint
class LocationsActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()
    private val helper = DeviceLocationHelper()
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        initPlaces()
        initLayoutManager()

        btn_choose_location.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN,
                fields
            )
                .build(this)
            startActivityForResult(
                intent,
                AUTOCOMPLETE_REQUEST_CODE
            )
        }

        val adapter = ChosenLocationsAdapter(this, viewModel)

        subscribeObserver(adapter)

        choose_current_location.setOnClickListener {

            if (helper.checkPermissions() && helper.isGPSEnabled) {
                prefs = PreferenceManager.getDefaultSharedPreferences(this)
                prefs.edit().putInt(LOCATION_TYPE, LOCATION_TYPE_CURRENT).apply()
            } else if (helper.checkPermissions()) {
                helper.initGPSDialog(this)
            } else helper.requestPermission(this)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getChosenLocations()
    }

    private fun initLayoutManager() {
        rv_chosen_locations.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initPlaces() {
        Places.initialize(this, "AIzaSyDmqc6zOhiHyGbd87ru4iqeGZUPINnbVc4")
        Places.createClient(this)
    }

    private fun subscribeObserver(adapter: ChosenLocationsAdapter) {

        viewModel.getChosenLocations().observe(this,
            Observer<List<ChosenLocation>> {
                adapter.setChosenLocations(it)
                rv_chosen_locations.adapter = adapter

            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        viewModel.insertChosenLocation(
                            ChosenLocation(
                                place.id!!,
                                place.name!!,
                                place.address!!,
                                place.latLng!!.latitude,
                                place.latLng!!.longitude
                            )
                        )
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {

                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, status.statusMessage)
                    }
                }
                Activity.RESULT_CANCELED -> {
                }
            }
            return
        }

        if (requestCode == 1234) {
            helper.isGPSEnabled = helper.locationManager.isLocationEnabled
            if (helper.isGPSEnabled) {
                prefs = PreferenceManager.getDefaultSharedPreferences(this)
                prefs.edit().putInt(LOCATION_TYPE, LOCATION_TYPE_CURRENT).apply()
            } else helper.initGPSDialog(this)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            AppSettingsDialog.Builder(this).build().show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (helper.isGPSEnabled) {
            prefs = PreferenceManager.getDefaultSharedPreferences(this)
            prefs.edit().putInt(LOCATION_TYPE, LOCATION_TYPE_CURRENT).apply()
        } else helper.initGPSDialog(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }



}