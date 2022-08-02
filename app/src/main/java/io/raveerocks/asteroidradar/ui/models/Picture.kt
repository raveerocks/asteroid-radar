package io.raveerocks.asteroidradar.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Picture(
    val copyright: String = "",
    val date: String,
    val explanation: String,
    val hdURL: String,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String
) : Parcelable