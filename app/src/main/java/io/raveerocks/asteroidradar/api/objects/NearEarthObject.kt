package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class NearEarthObject(
    val links: SelfLinksObject,
    val id: String,
    @Json(name = "neo_reference_id")
    val neoReferenceId: String,
    val name: String,
    @Json(name = "nasa_jpl_url")
    val nasaJplUrl: String,
    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitudeH: Double,
    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameterObject,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,
    @Json(name = "close_approach_data")
    val closeApproachData: List<CloseApproachDatumObject>,
    @Json(name = "is_sentry_object")
    val isSentryObject: Boolean
) : Parcelable