package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstimatedDiameterUnitObject(
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: Double,
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double
) : Parcelable