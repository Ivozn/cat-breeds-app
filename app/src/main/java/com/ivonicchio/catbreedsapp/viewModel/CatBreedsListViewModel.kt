package com.ivonicchio.catbreedsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivonicchio.catbreedsapp.core.network.Resource.Status.ERROR
import com.ivonicchio.catbreedsapp.core.network.Resource.Status.SUCCESS
import com.ivonicchio.catbreedsapp.data.catsRepository.CatsRepository
import com.ivonicchio.catbreedsapp.model.CatBreed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatBreedsListViewModel(private val catsRepository: CatsRepository,
                             private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {

    private val _catBreedsList = MutableLiveData<List<CatBreed>>()
    val catBreedsList: LiveData<List<CatBreed>> = _catBreedsList

    private val _requestError = MutableLiveData<Boolean>()
    val requestError: LiveData<Boolean> = _requestError

    init {
        getCatBreedsList()
    }

    fun getCatBreedsList() {
        viewModelScope.launch(dispatcher) {
            val response = catsRepository.getCatBreedsList()

            when(response.status){
                SUCCESS -> {
                    response.data?.let {
                        _catBreedsList.postValue(it)
                    }
                }

                ERROR -> {
                    _requestError.postValue(true)
                }
            }
        }
    }

    fun clearRequestErrorEvent(){
        _requestError.value = false
    }
}