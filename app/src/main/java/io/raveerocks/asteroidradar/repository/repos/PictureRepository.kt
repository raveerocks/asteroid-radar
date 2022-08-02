package io.raveerocks.asteroidradar.repository.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.raveerocks.asteroidradar.BuildConfig
import io.raveerocks.asteroidradar.api.objects.PictureObject
import io.raveerocks.asteroidradar.api.services.PlanetaryAPIService
import io.raveerocks.asteroidradar.repository.ConverterUtil.asPicture
import io.raveerocks.asteroidradar.ui.models.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PictureRepository(private val planetaryAPIService: PlanetaryAPIService) {

    private val _pictureOfTheDay: MutableLiveData<Picture> = MutableLiveData()

    val pictureOfTheDay: LiveData<Picture>
        get() = _pictureOfTheDay

    suspend fun refreshAPOD(onLoad: () -> Unit, onFailure: () -> Unit) {
        withContext(Dispatchers.IO) {
            planetaryAPIService.getAstronomyPOD(BuildConfig.API_KEY).enqueue(object :
                Callback<PictureObject> {
                override fun onFailure(call: Call<PictureObject>, t: Throwable) {
                    Timber.e(t)
                    onFailure()
                }

                override fun onResponse(
                    call: Call<PictureObject>,
                    response: Response<PictureObject>
                ) {
                    Timber.i("Picture URL fetched.")
                    _pictureOfTheDay.value = response.body()!!.asPicture()
                    onLoad()
                }
            })
        }
    }

}