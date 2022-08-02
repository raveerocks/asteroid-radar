package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissDistanceObject(
    val astronomical: Double,
    val lunar: Double,
    val kilometers: Double,
    val miles: Double
) : Parcelable