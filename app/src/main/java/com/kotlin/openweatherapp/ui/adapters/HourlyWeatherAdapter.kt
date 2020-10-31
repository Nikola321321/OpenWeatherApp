package com.kotlin.openweatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.model.Weather
import com.kotlin.openweatherapp.util.Preferences.provideConverter
import com.kotlin.openweatherapp.util.Preferences.unitTypeState
import com.kotlin.openweatherapp.util.TimeConverter.timeOfDayAMPM
import com.kotlin.openweatherapp.util.unitconverterfactory.UnitType
import com.squareup.picasso.Picasso

class HourlyWeatherAdapter(val weather: Weather) :
    RecyclerView.Adapter<HourlyWeatherViewHolder>() {

    private val url = "https://openweathermap.org/img/wn/"
    private val converter = provideConverter(unitTypeState)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_weather, parent, false)
        return HourlyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val temp = weather.hourlyWeather[position].temperatureHourly

        holder.temp.text = converter.convert(temp).toString() + "°"
        holder.timestamp.text = timeOfDayAMPM(weather.hourlyWeather[position].time)
        Picasso.get()
            .load(url + weather.hourlyWeather[position].weatherDescription[0].icon + "@2x.png")
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return weather.hourlyWeather.size
    }

}

class HourlyWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var timestamp: TextView = view.findViewById(R.id.tv_timestamp)
    var temp: TextView = view.findViewById(R.id.tv_temp)
    var icon: ImageView = view.findViewById(R.id.iv_icon)

    init {
        view.setOnClickListener {
            Toast.makeText(view.context, "Clicked!", Toast.LENGTH_SHORT).show()
        }
    }

}
