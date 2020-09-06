package com.ivonicchio.catbreedsapp.data.catsRepository.remote

import com.ivonicchio.catbreedsapp.core.network.RetrofitResponse
import com.ivonicchio.catbreedsapp.core.network.Resource
import com.ivonicchio.catbreedsapp.data.catsRepository.CatsRepository
import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.model.CatBreedImage

class CatsRemoteDataSourceImpl(private val catsService: CatsService) :
    CatsRepository {

    override suspend fun getCatBreedsList(): Resource<List<CatBreed>> {
        return RetrofitResponse { catsService.getCatBreedsList() }.result()
    }

    override suspend fun getCatBreedImage(breedId: String): Resource<List<CatBreedImage>> {
        return RetrofitResponse { catsService.getCatBreedImage(breedId) }.result()
    }
}