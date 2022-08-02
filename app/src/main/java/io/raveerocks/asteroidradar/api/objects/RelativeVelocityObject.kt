package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Parcelize
data class RelativeVelocityObject(
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: Double,
    @Json(name = "kilometers_per_hour")
    val kilometersPerHour: Double,
    @Json(name = "miles_per_hour")
    val milesPerHour: Double
) : Parcelable