package io.raveerocks.asteroidradar.api

import io.raveerocks.asteroidradar.Configurations.PREFETCH_LIMIT
import io.raveerocks.asteroidradar.DateUtil
import io.raveerocks.asteroidradar.api.objects.AsteroidObject
import io.raveerocks.asteroidradar.api.services.NeoAPIService
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NeoAPIServiceTest {

    private val neoAPIService: NeoAPIService = APIServiceRegistry.neoAPIService
    private val testAPIKey: String = "DEMO_KEY"

    @Test
    fun getFeedTest() {
        val today = DateUtil.getToday()
        neoAPIService.getFeed(
            testAPIKey,
            DateUtil.getToday(),
            DateUtil.addDay(today, PREFETCH_LIMIT)
        ).enqueue(object :
            Callback<AsteroidObject> {

            override fun onFailure(call: Call<AsteroidObject>, throwable: Throwable) {
                Assert.fail(throwable.message)
            }

            override fun onResponse(
                call: Call<AsteroidObject>,
                response: Response<AsteroidObject>
            ) {
                val asteroid: AsteroidObject = response.body()!!
                Assert.assertNotNull("Asteroid is null.",asteroid)
            }
        })
    }

}