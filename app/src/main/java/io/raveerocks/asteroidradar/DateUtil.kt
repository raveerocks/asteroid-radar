package io.raveerocks.asteroidradar


import io.raveerocks.asteroidradar.Configurations.STANDARD_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val dateFormat by lazy { SimpleDateFormat(STANDARD_DATE_FORMAT, Locale.getDefault()) }

    fun getToday(): String {
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun addDay(from: String, offset: Int): String {
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(from)!!
        calendar.add(Calendar.DAY_OF_YEAR, offset)
        return dateFormat.format(calendar.time)
    }
}