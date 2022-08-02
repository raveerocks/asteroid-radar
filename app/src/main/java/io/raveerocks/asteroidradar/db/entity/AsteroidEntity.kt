package io.raveerocks.asteroidradar.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.raveerocks.asteroidradar.db.DateConverter
import java.sql.Date


@TypeConverters(DateConverter::class)
@Entity(tableName = "asteroids")
data class AsteroidEntity constructor(
    @PrimaryKey
    val id: String,
    val name: String,
    val date: Date,
    val absoluteMagnitude: Double,
    val estimatedDiameterMax: Double,
    val isPotentiallyHazardousAsteroid: Boolean,
    val relativeVelocity: Double,
    val missDistance: Double
)