package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstimatedDiameterObject(
    val kilometers: EstimatedDiameterUnitObject,
    val meters: EstimatedDiameterUnitObject,
    val miles: EstimatedDiameterUnitObject,
    val feet: EstimatedDiameterUnitObject,
) : Parcelable