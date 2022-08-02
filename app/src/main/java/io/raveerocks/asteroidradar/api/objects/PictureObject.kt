package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureObject(
    val copyright: String = "",
    val date: String,
    val explanation: String,
    @Json(name = "hdurl")
    val hdURL: String,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "service_version")
    val serviceVersion: String,
    val title: String,
    val url: String
) : Parcelable