package com.ivonicchio.catbreedsapp.data.catsRepository.remote

import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.model.CatBreedImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsService {

    @GET("v1/breeds")
    suspend fun getCatBreedsList(): Response<List<CatBreed>>

    @GET("v1/images/search")
    suspend fun getCatBreedImage(@Query("breed_id") breedId: String): Response<List<CatBreedImage>>
}