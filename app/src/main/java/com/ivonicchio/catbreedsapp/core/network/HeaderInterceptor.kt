package com.ivonicchio.catbreedsapp.core.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {

    private val headerApiKeyConst = "x-api-key"
    private val apiKeyValue = "c53f0816-4c4f-4982-b910-e9fb1c581ee2"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder().apply {
            addHeader(headerApiKeyConst, apiKeyValue)
        }.build()

        return chain.proceed(newRequest)
    }
}