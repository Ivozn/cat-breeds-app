package com.ivonicchio.catbreedsapp.data.catsRepository

import com.ivonicchio.catbreedsapp.core.network.Resource
import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.model.CatBreedImage

class CatsRepositoryImpl(private val catsRemoteDataSource: CatsRepository): CatsRepository {

    override suspend fun getCatBreedsList(): Resource<List<CatBreed>> {
        return catsRemoteDataSource.getCatBreedsList()
    }

    override suspend fun getCatBreedImage(breedId: String): Resource<List<CatBreedImage>> {
        return catsRemoteDataSource.getCatBreedImage(breedId)
    }
}