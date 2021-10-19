package com.example.spaceinformer.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.repository.favouritesrepo.FavouritesRepo
import com.example.spaceinformer.repository.ivrepo.IVRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repoImpl: IVRepositoryImpl,
    private val roomFavouritesRepo: FavouritesRepo
) : ViewModel() {

    //TODO: make ui react to errors
    private val _favourites = MutableLiveData<MutableList<IvItem>>()
    val favourites: LiveData<MutableList<IvItem>> get() = _favourites

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
                    it.forEach { id ->
                        val item = repoImpl.getIVWithNasaId(id)
                        withContext(Dispatchers.Main) {
                            if (item.status != RepositoryResponse.Status.ERROR) {
                                item.data.apply {
                                    this?.data?.first()?.favourite = true
                                }
                                addToFavouritesList(item.data!!)
                                _favouritesLoadingError.value = false
                                _loadingStatus.value = false
                            } else {
                                _favouritesLoadingError.value = true
                                _errorMessage.value = item.message!!
                                _loadingStatus.value = false
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addToFavouritesList(item: IvItem){
        _favourites.value?.add(item)
        _favourites.value = _favourites.value
    }
}