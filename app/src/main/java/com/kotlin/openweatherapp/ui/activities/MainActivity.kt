package com.kotlin.openweatherapp.ui.activities

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.kotlin.openweatherapp.MyApplication
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.ui.fragments.CurrentWeatherFragment
import com.kotlin.openweatherapp.ui.fragments.DailyWeatherFragment
import com.kotlin.openweatherapp.ui.fragments.HourlyWeatherFragment
import com.kotlin.openweatherapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private var prefs: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MyApplication.instance)
    private var currentType = prefs.getString("list_preference_1", "")
    private val helper = DeviceLocationHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (prefs.getInt(LOCATION_TYPE, LOCATION_TYPE_DEFAULT) != LOCATION_TYPE_DEFAULT)
            initUIWithPermissions()
        else showNoLocationDialogAtStart()
    }


    private fun initUIWithPermissions() {
        if (prefs.getInt(LOCATION_TYPE, LOCATION_TYPE_DEFAULT) == LOCATION_TYPE_CURRENT) {
            if (helper.checkPermissions() && helper.isGPSEnabled) {
                initToolbar()
                initFragments()
            } else if (helper.checkPermissions()) {
                helper.initGPSDialog(this)
            } else helper.requestPermission(this)
        } else {
            initToolbar()
            initFragments()
        }
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_current_weather, CurrentWeatherFragment())
            replace(R.id.fragment_daily_weather, DailyWeatherFragment())
            replace(R.id.fragment_hourly_weather, HourlyWeatherFragment())
            commit()
        }
    }


    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.inflateMenu(R.menu.main_menu)
    }

    override fun onResume() {
        super.onResume()

        if (currentType != prefs.getString("list_preference_1", "")) {
            currentType = prefs.getString("list_preference_1", "")
            initFragments()
        }

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            AppSettingsDialog.Builder(this).build().show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (helper.isGPSEnabled) {
            initToolbar()
            initFragments()
        } else helper.initGPSDialog(this)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GPS_SETTINGS_STATUS_CHANGE_REQUEST_CODE -> {
                helper.isGPSEnabled = helper.locationManager.isLocationEnabled
                if (helper.isGPSEnabled) {
                    initToolbar()
                    initFragments()
                } else helper.initGPSDialog(this)
            }

            LOCATION_NOT_YET_SELECTED_REQUEST_CODE -> {
                if (prefs.getInt(
                        LOCATION_TYPE,
                        LOCATION_TYPE_DEFAULT
                    ) != LOCATION_TYPE_DEFAULT
                ) {
                    initUIWithPermissions()
                } else {
                    showLocationNotSelectedDialog()
                }
            }
        }
    }

    private fun showLocationNotSelectedDialog() {
        AlertDialog.Builder(this)
            .setMessage("Location not chosen!")
            .setPositiveButton("OK") { dialog, which ->

                exitProcess(0)
            }
            .create()
            .show()
    }

    private fun showNoLocationDialogAtStart() {
        AlertDialog.Builder(this)
            .setTitle("Location not chosen!")
            .setMessage("You must choose a location!")
            .setPositiveButton("OK") { dialog, which ->
                startActivityForResult(
                    Intent(this, LocationsActivity::class.java),
                    LOCATION_NOT_YET_SELECTED_REQUEST_CODE
                )
            }
            .setNegativeButton("Cancel") { dialog, which ->
                exitProcess(0)
            }
            .create()
            .show()
    }

    fun loadSettingsActivity(menuItem: MenuItem) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun loadLocationsActivity(item: MenuItem) {
        startActivity(Intent(this, LocationsActivity::class.java))
    }


}



