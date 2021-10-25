package com.example.spaceinformer.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.repository.ivrepo.IVRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repoImpl: IVRepositoryImpl) : ViewModel() {

    //TODO: make ui react to errors
    private val _favourites = MutableLiveData<MutableList<DomainIvItem>>()
    val favourites: LiveData<MutableList<DomainIvItem>> get() = _favourites

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
                repoImpl.getAllFavouritesIds().collect {
                    it.forEach { id ->
                        val item = repoImpl.getIVWithNasaId(id)
                        withContext(Dispatchers.Main) {
                            if (item.status != RepositoryResponse.Status.ERROR) {
                                item.data.apply {
                                    this?.favourite = true
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


    private fun addToFavouritesList(itemApp: DomainIvItem){
        _favourites.value?.add(itemApp)
        _favourites.value = _favourites.value
    }
}