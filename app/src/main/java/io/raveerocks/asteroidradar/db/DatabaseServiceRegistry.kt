package io.raveerocks.asteroidradar.db

import android.content.Context
import androidx.room.Room
import io.raveerocks.asteroidradar.Configurations.CACHE_DATABASE_NAME
import io.raveerocks.asteroidradar.db.database.CacheDatabase

class DatabaseServiceRegistry(context: Context) {

    val testDatabase: CacheDatabase by lazy {
        Room.inMemoryDatabaseBuilder(context, CacheDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    val cacheDatabase: CacheDatabase by lazy {
        Room.databaseBuilder(
            context,
            CacheDatabase::class.java,
            CACHE_DATABASE_NAME
        ).build()
    }

}