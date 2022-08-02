package io.raveerocks.asteroidradar

import android.app.Application
import android.content.Context
import io.raveerocks.asteroidradar.jobs.WorkerRegistry
import io.raveerocks.asteroidradar.jobs.workers.RefreshCacheWorker
import io.raveerocks.asteroidradar.repository.RepositoryRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class AsteroidRadarApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, "App/$tag", message, t)
            }
        })
        RepositoryRegistry.initialise(applicationContext)
        setupBackgroundWork(applicationContext)
    }

    private fun setupBackgroundWork(applicationContext: Context) {
        applicationScope.launch {
            setupCacheRefresh(applicationContext)
        }
    }

    private fun setupCacheRefresh(applicationContext: Context) {
        WorkerRegistry(applicationContext).runCacheRefresh()
    }
}