package com.kotlin.openweatherapp.util

import java.time.LocalDateTime
import java.time.ZoneOffset

object TimeConverter {

    fun convertTime(epochTime: Int): LocalDateTime {
        return LocalDateTime.ofEpochSecond(epochTime.toLong(), 0, ZoneOffset.ofTotalSeconds(7200))
    }

    fun timeOfDayAMPM(time: Int): String {
        val timeOfDay = convertTime(time).hour
        when (timeOfDay) {
            in 1..11 -> return "$timeOfDay AM"
            12 -> return "$timeOfDay PM"
            in 13..23 -> return "${timeOfDay - 12} PM"
            0 -> return "${timeOfDay + 12} AM"
        }
        return ""
    }
}