package io.raveerocks.asteroidradar.api.objects

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CloseApproachDatumObject(

    @Json(name = "close_approach_date")
    val closeApproachDate: String,
    @Json(name = "close_approach_date_full")
    val closeApproachDateFull: String,
    @Json(name = "epoch_date_close_approach")
    val epochDateCloseApproach: String,
    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocityObject,
    @Json(name = "miss_distance")
    val missDistance: MissDistanceObject,
    @Json(name = "orbiting_body")
    val orbitingBody: String
) : Parcelable