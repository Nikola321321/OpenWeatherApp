package com.kotlin.openweatherapp.ui.activities

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.kotlin.openweatherapp.MyApplication
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.ui.fragments.CurrentWeatherFragment
import com.kotlin.openweatherapp.ui.fragments.DailyWeatherFragment
import com.kotlin.openweatherapp.ui.fragments.HourlyWeatherFragment
import com.kotlin.openweatherapp.util.DeviceLocationHelper
import com.kotlin.openweatherapp.util.LOCATION_TYPE
import com.kotlin.openweatherapp.util.LOCATION_TYPE_CURRENT
import com.kotlin.openweatherapp.util.LOCATION_TYPE_DEFAULT
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private var prefs: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MyApplication.instance)
    private var currentType = prefs.getString("list_preference_1", "")
    private val helper = DeviceLocationHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUIWithPermissions()
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
        if (requestCode == 1234) {
            helper.isGPSEnabled = helper.locationManager.isLocationEnabled
            if (helper.isGPSEnabled) {
                initToolbar()
                initFragments()
            } else helper.initGPSDialog(this)
        }
    }

    fun loadSettingsActivity(menuItem: MenuItem) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun loadLocationsActivity(item: MenuItem) {
        startActivity(Intent(this, LocationsActivity::class.java))
    }

}



