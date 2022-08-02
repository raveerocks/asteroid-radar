package io.raveerocks.asteroidradar.api.services

import io.raveerocks.asteroidradar.api.objects.AsteroidObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NeoAPIService {
    @GET("feed")
    fun getFeed(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Call<AsteroidObject>
}