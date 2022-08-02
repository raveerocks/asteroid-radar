package io.raveerocks.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIServiceUtil {

    private val moshi by lazy {
        MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )
    }

    fun <T : Any> getServiceInstance(baseURL: String, service: Class<T>): T {
        return Retrofit.Builder()
            .addConverterFactory(moshi)
            .baseUrl(baseURL)
            .build()
            .create(service)
    }
}