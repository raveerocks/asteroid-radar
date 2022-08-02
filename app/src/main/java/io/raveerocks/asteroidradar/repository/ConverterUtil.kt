package io.raveerocks.asteroidradar.repository


import io.raveerocks.asteroidradar.api.objects.AsteroidObject
import io.raveerocks.asteroidradar.api.objects.PictureObject
import io.raveerocks.asteroidradar.db.entity.AsteroidEntity
import io.raveerocks.asteroidradar.ui.models.Asteroid
import io.raveerocks.asteroidradar.ui.models.Picture
import java.sql.Date

object ConverterUtil {

    fun PictureObject.asPicture(): Picture {
        return Picture(copyright, date, explanation, hdURL, mediaType, serviceVersion, title, url)
    }

    fun List<AsteroidEntity>.asAsteroid(): List<Asteroid> {
        return map {
            Asteroid(
                it.id,
                it.name,
                it.date.toString(),
                it.absoluteMagnitude,
                it.estimatedDiameterMax,
                it.isPotentiallyHazardousAsteroid,
                it.relativeVelocity,
                it.missDistance
            )
        }
    }

    fun AsteroidObject.asAsteroidEntityByDate(dates: List<String>): Array<AsteroidEntity> {
        val asteroids = ArrayList<AsteroidEntity>()
        for (date in dates) {
            if (nearEarthObjects.containsKey(date)) {
                for (nearEarthObject in nearEarthObjects[date]!!) {
                    asteroids.add(
                        AsteroidEntity(
                            nearEarthObject.id,
                            nearEarthObject.name,
                            Date.valueOf(date),
                            nearEarthObject.absoluteMagnitudeH,
                            nearEarthObject.estimatedDiameter.kilometers.estimatedDiameterMax,
                            nearEarthObject.isPotentiallyHazardousAsteroid,
                            nearEarthObject.closeApproachData[0].relativeVelocity.kilometersPerHour,
                            nearEarthObject.closeApproachData[0].missDistance.kilometers
                        )
                    )
                }
            }
        }
        return asteroids.toTypedArray()
    }

}