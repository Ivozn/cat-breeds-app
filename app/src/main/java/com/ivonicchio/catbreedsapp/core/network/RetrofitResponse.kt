package com.ivonicchio.catbreedsapp.core.network

import retrofit2.Response


class RetrofitResponse<T>(
    private val request: suspend () -> Response<T>
) {

    suspend fun result(): Resource<T> {
        return try {
            val response = request.invoke()
            val data = response.body()

            if (response.isSuccessful && data != null) {
                Resource.success(data)
            } else {
                Resource.error()
            }
        } catch (exception: Exception) {
            Resource.error()
        }
    }
}