package com.ivonicchio.catbreedsapp.core.network

import com.ivonicchio.catbreedsapp.core.network.Resource.Status.ERROR
import com.ivonicchio.catbreedsapp.core.network.Resource.Status.SUCCESS
import com.squareup.moshi.JsonDataException
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import retrofit2.Response

class RetrofitResponseTest{

    private val response = mockk<Response<Boolean>>()

    @Test
    fun `when the request is successful with a body, return a SUCCESS status`() {
        // Arrange
        every { response.isSuccessful } answers { true }
        every { response.body() } answers { true }

        // Act
        val retrofitResponse = runBlocking {
            RetrofitResponse { response }
                .result()
        }

        // Assert
        assertEquals(SUCCESS, retrofitResponse.status)
    }

    @Test
    fun `when the request is successful WITHOUT a body, return a ERROR status`() {
        // Arrange
        every { response.isSuccessful } answers { true }
        every { response.body() } answers { null }

        // Act
        val retrofitResponse = runBlocking {
            RetrofitResponse { response }
                .result()
        }

        // Assert
        assertEquals(ERROR, retrofitResponse.status)
    }

    @Test
    fun `when the request is NOT successful, return a ERROR status`() {
        // Arrange
        every { response.isSuccessful } answers { false }
        every { response.body() } answers { true }

        // Act
        val retrofitResponse = runBlocking {
            RetrofitResponse { response }
                .result()
        }

        // Assert
        assertEquals(ERROR, retrofitResponse.status)
    }

    @Test
    fun `when the request is successful but throws a exception, return a ERROR status`() {
        // Arrange
        every { response.isSuccessful } answers { true }
        every { response.body() } throws JsonDataException()

        // Act
        val retrofitResponse = runBlocking {
            RetrofitResponse { response }
                .result()
        }

        // Assert
        assertEquals(ERROR, retrofitResponse.status)
    }
}