package com.kotlin.openweatherapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.cachedb.ChosenLocation
import com.kotlin.openweatherapp.ui.viewmodels.MainViewModel
import com.kotlin.openweatherapp.util.*


class ChosenLocationsAdapter(val context: Context, private val viewModel: MainViewModel) :
    RecyclerView.Adapter<ChosenLocationsViewHolder>() {

    private var chosenLocations: List<ChosenLocation> = mutableListOf()
    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(context).edit() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenLocationsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_chosen_location, parent, false)
        return ChosenLocationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChosenLocationsViewHolder, position: Int) {
        val chosenLocation = chosenLocations[position]
        holder.cityName.text = chosenLocation.cityName
        holder.cityAddress.text = chosenLocation.cityAddress

        deleteChosenLocationListener(holder, chosenLocation)
        chooseLocationListener(holder, chosenLocation)
    }

    override fun getItemCount(): Int {
        return chosenLocations.size
    }

    fun setChosenLocations(chosenLocations: List<ChosenLocation>) {
        this.chosenLocations = chosenLocations
    }

    private fun chooseLocationListener(
        holder: ChosenLocationsViewHolder,
        chosenLocation: ChosenLocation
    ) {
        holder.itemView.setOnClickListener {
            prefs.putInt(LOCATION_TYPE, LOCATION_TYPE_CHOSEN)
            prefs.putString(LOCATION_CITY_NAME, chosenLocation.cityName)
            prefs.putFloat(LOCATION_CITY_LAT, chosenLocation.cityLatitude.toFloat())
            prefs.putFloat(LOCATION_CITY_LON, chosenLocation.cityLongitude.toFloat())
            prefs.apply()
        }
    }

    private fun deleteChosenLocationListener(
        holder: ChosenLocationsViewHolder,
        chosenLocation: ChosenLocation
    ) {
        holder.deleteChosenLocation.setOnClickListener {
            viewModel.deleteChosenLocation(chosenLocation)
        }
    }
}

 class ChosenLocationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var cityName: TextView = view.findViewById(R.id.tv_chosen_city_name)
    var cityAddress: TextView = view.findViewById(R.id.tv_chosen_city_address)
    var deleteChosenLocation: ImageView = view.findViewById(R.id.iv_delete_chosen_location)
    }
