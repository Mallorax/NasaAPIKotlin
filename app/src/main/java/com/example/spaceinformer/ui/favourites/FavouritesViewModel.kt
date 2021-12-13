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
    private val repoImpl: IVRepositoryImpl
) : ViewModel() {

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


    fun loadFavourites() {
        viewModelScope.launch {
            _loadingStatus.value = true
            withContext(Dispatchers.IO) {
                repoImpl.getEveryItemInFavourites().collect {
                    it.forEach { t ->
                        if (t.isFavourite) {
                            val item = repoImpl.getIVWithNasaId(t.nasaId)
                            withContext(Dispatchers.Main) {
                                if (item.status != RepositoryResponse.Status.ERROR) {
                                    item.data?.favourite = t.isFavourite
                                    updateFavouritesList(item.data!!)
                                    _favouritesLoadingError.value = false
                                    _loadingStatus.value = false
                                } else {
                                    _favouritesLoadingError.value = true
                                    _errorMessage.value = item.message!!
                                    _loadingStatus.value = false
                                }
                            }
                        }else{
                            withContext(Dispatchers.Main){
                                removeItemFromList(t.nasaId)
                                _favouritesLoadingError.value = false
                                _loadingStatus.value = false
                            }
                        }
                    }
                }
            }
        }
    }


    private fun updateFavouritesList(itemApp: DomainIvItem) {
        val favList = _favourites.value
        if (!favList!!.any {t -> t.nasaId == itemApp.nasaId  }){
            favList.add(itemApp)
        }
        _favourites.value = favList!!
    }

    private fun removeItemFromList(id: String){
        val favList = _favourites.value
        favList?.removeAll { t-> t.nasaId == id }
        _favourites.value = favList!!
    }

    fun updateFavourite(data: DomainIvItem) {
        if (!data.favourite) {
            _favourites.value?.removeAll { t -> t.nasaId == data.nasaId }
            _favourites.value = _favourites.value
        }
        viewModelScope.launch(Dispatchers.IO) {
            repoImpl.saveToFavourites(data)
        }
    }
}