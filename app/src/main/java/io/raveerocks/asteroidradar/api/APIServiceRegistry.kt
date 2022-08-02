package io.raveerocks.asteroidradar.api

import io.raveerocks.asteroidradar.Configurations.NEO_API_BASE_URL
import io.raveerocks.asteroidradar.Configurations.PLANETARY_API_BASE_URL
import io.raveerocks.asteroidradar.api.APIServiceUtil.getServiceInstance
import io.raveerocks.asteroidradar.api.services.NeoAPIService
import io.raveerocks.asteroidradar.api.services.PlanetaryAPIService

object APIServiceRegistry {

    val neoAPIService: NeoAPIService by lazy {
        getServiceInstance(
            NEO_API_BASE_URL,
            NeoAPIService::class.java
        )
    }
    val planetaryAPIService: PlanetaryAPIService by lazy {
        getServiceInstance(
            PLANETARY_API_BASE_URL,
            PlanetaryAPIService::class.java
        )
    }

}

