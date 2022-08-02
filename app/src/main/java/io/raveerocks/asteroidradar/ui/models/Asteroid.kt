package io.raveerocks.asteroidradar.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: String,
    val name: String,
    val date: String,
    val absoluteMagnitude: Double,
    val estimatedDiameterMax: Double,
    val isPotentiallyHazardousAsteroid: Boolean,
    val relativeVelocity: Double,
    val missDistance: Double
) : Parcelable