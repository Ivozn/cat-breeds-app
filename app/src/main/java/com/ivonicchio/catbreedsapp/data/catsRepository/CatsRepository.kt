package com.ivonicchio.catbreedsapp.data.catsRepository

import com.ivonicchio.catbreedsapp.core.network.Resource
import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.model.CatBreedImage

interface CatsRepository {
    suspend fun getCatBreedsList(): Resource<List<CatBreed>>
    suspend fun getCatBreedImage(breedId: String): Resource<List<CatBreedImage>>
}