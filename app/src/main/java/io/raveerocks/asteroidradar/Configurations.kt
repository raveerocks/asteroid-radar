package io.raveerocks.asteroidradar

import java.util.concurrent.TimeUnit

object Configurations {

    const val NEO_API_BASE_URL = "https://api.nasa.gov/neo/rest/v1/"
    const val PLANETARY_API_BASE_URL = "https://api.nasa.gov/planetary/"

    const val CACHE_DATABASE_NAME = "cache"
    const val CACHE_REFRESH_WORKER_NAME = "CacheRefreshWorker"

    const val STANDARD_DATE_FORMAT = "yyyy-MM-dd"
    const val PREFETCH_LIMIT = 7
    const val PREFETCH_FREQUENCY = 1L
    val PREFETCH_FREQUENCY_UNIT = TimeUnit.DAYS
}