package com.example.dataapiexercise.network

import com.example.dataapiexercise.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface ZingApiService {

    /**
     * getting data from api
     */
    @GET("xhr/chart-realtime?songId=0&videoId=0&albumId=0&chart=song&time=-1")
    suspend fun getZingChart(): ZingResponse


    /**
     * creating instance of zingapiservice with moshi to parse json data
     */
    companion object {
        fun create(): ZingApiService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ZingApiService::class.java)
        }
    }
}
