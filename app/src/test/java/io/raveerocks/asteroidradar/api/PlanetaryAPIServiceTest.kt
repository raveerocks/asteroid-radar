package io.raveerocks.asteroidradar.api

import io.raveerocks.asteroidradar.api.objects.PictureObject
import io.raveerocks.asteroidradar.api.services.PlanetaryAPIService
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanetaryAPIServiceTest {

    private val planetaryAPIService: PlanetaryAPIService = APIServiceRegistry.planetaryAPIService
    private val testAPIKey: String = "DEMO_KEY"

    @Test
    fun getFeedTest() {
        planetaryAPIService.getAstronomyPOD(
            testAPIKey).enqueue(object :
            Callback<PictureObject> {

            override fun onFailure(call: Call<PictureObject>, throwable: Throwable) {
                Assert.fail(throwable.message)
            }

            override fun onResponse(
                call: Call<PictureObject>,
                response: Response<PictureObject>
            ) {
                val picture: PictureObject = response.body()!!
                Assert.assertNotNull("Picture is null.",picture)
            }
        })
    }

}