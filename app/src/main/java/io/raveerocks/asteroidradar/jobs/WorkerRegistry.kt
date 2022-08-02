package io.raveerocks.asteroidradar.jobs

import android.content.Context
import androidx.work.*
import io.raveerocks.asteroidradar.Configurations.CACHE_REFRESH_WORKER_NAME
import io.raveerocks.asteroidradar.Configurations.PREFETCH_FREQUENCY
import io.raveerocks.asteroidradar.Configurations.PREFETCH_FREQUENCY_UNIT
import io.raveerocks.asteroidradar.jobs.workers.RefreshCacheWorker

class WorkerRegistry(private val context: Context) {

    companion object{

        private val constraints by lazy {
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .build()
        }

        private val repeatingRequest by lazy {
            PeriodicWorkRequestBuilder<RefreshCacheWorker>(PREFETCH_FREQUENCY, PREFETCH_FREQUENCY_UNIT)
                .setConstraints(constraints)
                .build()
        }
    }

    fun runCacheRefresh() {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            CACHE_REFRESH_WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}