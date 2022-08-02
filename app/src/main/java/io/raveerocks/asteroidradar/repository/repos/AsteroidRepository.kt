package io.raveerocks.asteroidradar.repository.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.raveerocks.asteroidradar.BuildConfig
import io.raveerocks.asteroidradar.DateUtil.addDay
import io.raveerocks.asteroidradar.DateUtil.getDateList
import io.raveerocks.asteroidradar.api.objects.AsteroidObject
import io.raveerocks.asteroidradar.api.services.NeoAPIService
import io.raveerocks.asteroidradar.db.database.CacheDatabase
import io.raveerocks.asteroidradar.repository.ConverterUtil.asAsteroid
import io.raveerocks.asteroidradar.repository.ConverterUtil.asAsteroidEntityByDate
import io.raveerocks.asteroidradar.ui.models.Asteroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.sql.Date

class AsteroidRepository(
    private val database: CacheDatabase,
    private val neoAPIService: NeoAPIService
) {

    fun viewAllAsteroids(date: String): LiveData<List<Asteroid>> {
        return Transformations.map(database.asteroidDAO.getAllAsteroids(Date.valueOf(date))) {
            it.asAsteroid()
        }
    }

    fun filterAsteroidsByDay(date: String): LiveData<List<Asteroid>> {
        return Transformations.map(database.asteroidDAO.getAllAsteroidsByDate(Date.valueOf(date))) {
            it.asAsteroid()
        }
    }

    suspend fun refreshAsteroids(
        from: String,
        offset: Int,
        onLoad: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            neoAPIService.getFeed(BuildConfig.API_KEY, from, addDay(from, offset)).enqueue(object :
                Callback<AsteroidObject> {

                override fun onFailure(call: Call<AsteroidObject>, throwable: Throwable) {
                    onFailure(throwable)
                }

                override fun onResponse(
                    call: Call<AsteroidObject>,
                    response: Response<AsteroidObject>
                ) {
                    val asteroids =
                        response.body()!!.asAsteroidEntityByDate(getDateList(from, offset))
                    CoroutineScope(Dispatchers.IO).launch {
                        database.asteroidDAO.insertAll(*asteroids)
                    }
                    onLoad()
                }
            })
        }
    }

    fun deleteOldAsteroids(date: String, onComplete: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val dataDeleted = database.asteroidDAO.deleteOldData(Date.valueOf(date))
            Timber.i("$dataDeleted entries deleted.")
            onComplete()
        }
    }
}