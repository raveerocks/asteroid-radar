package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksObject(val next: String, val prev: String, val self: String) : Parcelable