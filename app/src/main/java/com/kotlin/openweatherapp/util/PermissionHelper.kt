package com.kotlin.openweatherapp.util

import android.Manifest
import android.app.Activity
import android.content.*
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kotlin.openweatherapp.MyApplication
import pub.devrel.easypermissions.EasyPermissions

private const val LOCATION_PERMISSIONS_REQUEST_CODE = 9001
private val requestedPermissions = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

class DeviceLocationHelper {



    var locationManager =
        MyApplication.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    var isGPSEnabled = locationManager.isLocationEnabled

     fun checkPermissions(): Boolean {
        if (EasyPermissions.hasPermissions(
                MyApplication.instance,
                *requestedPermissions
            )
        )  return true
        return false
    }

     fun requestPermission(activity: Activity){
        EasyPermissions.requestPermissions(
            activity,
            "Permissions needed",
            LOCATION_PERMISSIONS_REQUEST_CODE,
            *requestedPermissions
        )
    }


     fun initGPSDialog(activity: Activity) {
        AlertDialog.Builder(activity)
            .setMessage("GPS is needed for the app to run")
            .setPositiveButton("Turn on!") { dialog, which ->
                activity.startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1234)

            }
            .setNegativeButton("cancel") { dialog, which ->
                Toast.makeText(activity, "Choose location manually", Toast.LENGTH_SHORT).show()

                dialog.dismiss()
            }
            .create()
            .show()

    }



}