package io.raveerocks.asteroidradar.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.raveerocks.asteroidradar.db.dao.AsteroidDAO
import io.raveerocks.asteroidradar.db.entity.AsteroidEntity

@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {
    abstract val asteroidDAO: AsteroidDAO
}