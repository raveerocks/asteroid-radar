package io.raveerocks.asteroidradar.api.services

import io.raveerocks.asteroidradar.api.objects.PictureObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetaryAPIService {
    @GET("apod")
    fun getAstronomyPOD(@Query("api_key") apiKey: String): Call<PictureObject>
}