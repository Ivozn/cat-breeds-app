package com.ivonicchio.catbreedsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivonicchio.catbreedsapp.core.network.Resource.Status.ERROR
import com.ivonicchio.catbreedsapp.core.network.Resource.Status.SUCCESS
import com.ivonicchio.catbreedsapp.data.catsRepository.CatsRepository
import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.model.CatBreedImage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatBreedDetailViewModel(
    private val catsRepository: CatsRepository,
    val catBreed: CatBreed,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

) : ViewModel() {

    companion object {
        const val CONST_CAT_BREED = "CONST_CAT_BREED"
    }

    private val _catBreedImage = MutableLiveData<List<CatBreedImage>>()
    val catBreedsImage: LiveData<List<CatBreedImage>> = _catBreedImage

    private val _requestError = MutableLiveData<Boolean>()
    val requestError: LiveData<Boolean> = _requestError

    init {
        getCatBreedImage()
    }

    fun getCatBreedImage() {
        viewModelScope.launch(dispatcher) {
            val response = catsRepository.getCatBreedImage(catBreed.id)

            when (response.status) {
                SUCCESS -> {
                    response.data?.let {
                        _catBreedImage.postValue(it)
                    }
                }

                ERROR -> {
                    _requestError.postValue(true)
                }
            }
        }
    }

    fun clearRequestErrorEvent() {
        _requestError.value = false
    }


    
}
