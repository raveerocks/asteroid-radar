package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class AsteroidObject(
    val links: LinksObject,
    @Json(name = "element_count")
    val elementCount: Int,
    @Json(name = "near_earth_objects")
    val nearEarthObjects: Map<String, List<NearEarthObject>>
) : Parcelable








