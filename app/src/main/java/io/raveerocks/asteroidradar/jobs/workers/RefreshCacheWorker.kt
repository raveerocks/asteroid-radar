package io.raveerocks.asteroidradar.jobs.workers

import android.content.Context
import androidx.work.*
import io.raveerocks.asteroidradar.Configurations.PREFETCH_LIMIT
import io.raveerocks.asteroidradar.DateUtil.getToday
import io.raveerocks.asteroidradar.repository.RepositoryRegistry
import retrofit2.HttpException

class RefreshCacheWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            RepositoryRegistry.asteroidRepository.refreshAsteroids(
                getToday(),
                PREFETCH_LIMIT,
                {},
                { t: Throwable -> throw t })
            RepositoryRegistry.asteroidRepository.deleteOldAsteroids(getToday()) {}
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

}