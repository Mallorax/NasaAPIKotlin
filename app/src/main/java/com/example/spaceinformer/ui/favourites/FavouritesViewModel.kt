package com.example.spaceinformer.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.repository.favouritesrepo.FavouritesRepo
import com.example.spaceinformer.repository.ivrepo.IVRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val retrofitRepo: IVRetrofitRepository,
    private val roomFavouritesRepo: FavouritesRepo
) : ViewModel() {

    private val _favourites = MutableLiveData<MutableList<IvItem>>()
    private val favourites: LiveData<MutableList<IvItem>> get() = _favourites

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _favouritesLoadingError = MutableLiveData<Boolean>()
    val favouritesLoadingError: LiveData<Boolean> get() = _favouritesLoadingError

    private val _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus: LiveData<Boolean> get() = _loadingStatus

    init {
        _favourites.value = mutableListOf()
        _favouritesLoadingError.value = false
    }


    fun loadFavourites(){
        viewModelScope.launch {
            _loadingStatus.value = true
            withContext(Dispatchers.IO){
                roomFavouritesRepo.getAllFavourites().collect {
                    val item = retrofitRepo.getIVWithNasaId(it)
                    if (item.status != RepositoryResponse.Status.ERROR){
                        addToFavouritesList(item.data!!)
                        _favouritesLoadingError.value = false
                    }else{
                        _favouritesLoadingError.value = true
                        _errorMessage.value = item.message!!
                    }
                }
            }
            _loadingStatus.value = false
        }
    }

    private fun addToFavouritesList(item: IvItem){
        _favourites.value?.add(item)
        _favourites.value = _favourites.value
    }
}