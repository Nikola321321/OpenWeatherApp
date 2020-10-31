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
import com.kotlin.openweatherapp.util.Preferences
import com.kotlin.openweatherapp.util.Preferences.unitTypeState
import com.kotlin.openweatherapp.util.TimeConverter.convertTime
import com.squareup.picasso.Picasso

class DailyWeatherAdapter(val weather: Weather) :
    RecyclerView.Adapter<DailyWeatherViewHolder>() {

    private val url = "https://openweathermap.org/img/wn/"
    private val converter = Preferences.provideConverter(unitTypeState)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_weather, parent, false)
        return DailyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val temp = weather.dailyWeather[position].temperatureDaily.day


        holder.temp.text = converter.convert(temp).toString() + "°"
        holder.timestamp.text =
            convertTime(weather.dailyWeather[position].time).dayOfWeek.toString()
        Picasso.get()
            .load(url + weather.dailyWeather[position].weatherDescription[0].icon + "@2x.png")
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return 8
    }
}

class DailyWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var timestamp: TextView = view.findViewById(R.id.tv_timestamp)
    var temp: TextView = view.findViewById(R.id.tv_temp)
    var icon: ImageView = view.findViewById(R.id.iv_icon)

    init {

        view.setOnClickListener {
            Toast.makeText(view.context, "Clicked!", Toast.LENGTH_SHORT).show()
        }
    }


}