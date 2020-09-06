package com.ivonicchio.catbreedsapp.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitService {

    private val baseUrl = "https://api.thecatapi.com/"

    fun <S> createService(serviceClass: Class<S>): S {

        val client = createHttpClient()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(serviceClass)
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }
}