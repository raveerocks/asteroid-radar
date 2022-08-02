package io.raveerocks.asteroidradar.repository

import android.content.Context
import io.raveerocks.asteroidradar.api.APIServiceRegistry.neoAPIService
import io.raveerocks.asteroidradar.api.APIServiceRegistry.planetaryAPIService
import io.raveerocks.asteroidradar.db.DatabaseServiceRegistry
import io.raveerocks.asteroidradar.repository.repos.AsteroidRepository
import io.raveerocks.asteroidradar.repository.repos.PictureRepository

object RepositoryRegistry {

    private lateinit var databaseServiceRegistry: DatabaseServiceRegistry

    fun initialise(context: Context) {
        databaseServiceRegistry = DatabaseServiceRegistry(context)
    }

    val asteroidRepository: AsteroidRepository by lazy {
        AsteroidRepository(
            databaseServiceRegistry.cacheDatabase,
            neoAPIService
        )
    }
    val pictureRepository: PictureRepository by lazy { PictureRepository(planetaryAPIService) }
}