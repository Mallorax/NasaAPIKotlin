package com.example.spaceinformer.ui.imagesandvideos

import androidx.lifecycle.*
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.model.nasapi.imagesandpictures.Data
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.repository.ivrepo.IVRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IVViewModel
@Inject constructor(
    private val repoImpl: IVRepositoryImpl,
) : ViewModel() {

    //TODO: Add more transparency to errors

    private val _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus: LiveData<Boolean> get() = _loadingStatus

    private val _ivs = MutableLiveData<MutableList<DomainIvItem>>()
    val ivs: LiveData<MutableList<DomainIvItem>> get() = _ivs

    private val _itemsLoadingError = MutableLiveData<Boolean>()
    val itemsLoadingError: LiveData<Boolean> get() = _itemsLoadingError

    private var _favourites = MutableLiveData<MutableList<DomainIvItem>>()
    private var page = 1

    init {
        _ivs.value = mutableListOf<DomainIvItem>()

    }


    fun getIVs(year: Int) {
        viewModelScope.launch {
            _loadingStatus.value = true
            withContext(Dispatchers.IO) {
                val response = repoImpl.getIVFromYearDistinct(year, page)
                page++
                withContext(Dispatchers.Main) {
                    if (response.status != RepositoryResponse.Status.ERROR && response.data != null) {
                        _itemsLoadingError.value = false
                        addPageOfItems(response.data!!)
                    } else {
                        _itemsLoadingError.value = true
                    }
                }
            }
            _loadingStatus.value = false
            handleDbStateChanges()
        }
    }


    fun saveFavourite(data: DomainIvItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repoImpl.saveToFavourites(data)
        }
    }

    private fun addPageOfItems(items: List<DomainIvItem>) {
        _ivs.value?.addAll(items)
        _ivs.value = _ivs.value
    }

    private suspend fun handleDbStateChanges() {
        repoImpl.getAllFavourites().collect {
            if (_ivs.value != null) {
                it.forEach { fav ->
                    _ivs.value!!.find { iv -> iv.nasaId == fav.nasaId }.apply {
                        this?.favourite = fav.isFavourite
                    }
                    _ivs.value = _ivs.value
                }
            }
        }
    }


}