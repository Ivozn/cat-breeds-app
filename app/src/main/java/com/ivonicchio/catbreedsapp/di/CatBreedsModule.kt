package com.ivonicchio.catbreedsapp.di

import com.ivonicchio.catbreedsapp.core.network.RetrofitService
import com.ivonicchio.catbreedsapp.data.catsRepository.CatsRepository
import com.ivonicchio.catbreedsapp.data.catsRepository.CatsRepositoryImpl
import com.ivonicchio.catbreedsapp.data.catsRepository.remote.CatsRemoteDataSourceImpl
import com.ivonicchio.catbreedsapp.data.catsRepository.remote.CatsService
import com.ivonicchio.catbreedsapp.viewModel.CatBreedDetailViewModel
import com.ivonicchio.catbreedsapp.viewModel.CatBreedsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val CatBreedsModule = module {

    factory {
        CatsRemoteDataSourceImpl(
            RetrofitService().createService(CatsService::class.java)
        ) as CatsRepository
    }

    factory { CatsRepositoryImpl(get()) }

    viewModel { CatBreedsListViewModel(get()) }
    viewModel {
        CatBreedDetailViewModel(
            get(),
            getProperty(CatBreedDetailViewModel.CONST_CAT_BREED)
        )
    }
}