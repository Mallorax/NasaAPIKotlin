package com.example.spaceinformer.ui.imagesandvideos

import androidx.lifecycle.*
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.model.entities.DataEntity
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

    private val _searchedIVs = MutableLiveData<MutableList<DomainIvItem>>()
    val searchedIVs: LiveData<MutableList<DomainIvItem>> get() = _searchedIVs

    private var page = 1
    private var favs = mutableListOf<DataEntity>()

    private val loadingItem = DomainIvItem(mediaType = "loading")

    init {
        _ivs.value = mutableListOf()
        _searchedIVs.value = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            repoImpl.getAllFavourites().collect {
                favs = it.toMutableList()
                it.forEach {
                        _ivs.value!!.find { t -> t.nasaId == it.nasaId }.apply {
                            if (this != null){
                                this.favourite = it.isFavourite
                            }
                        }

                }
            }
        }
    }

    fun getIvsWithSearch(search: String){
        _searchedIVs.value?.add(loadingItem)
        _searchedIVs.value = _searchedIVs.value
        viewModelScope.launch(Dispatchers.IO) {
            val response = repoImpl.getIvsBySearch(search)
            withContext(Dispatchers.Main){
                if (response.status != RepositoryResponse.Status.ERROR && response.data != null){
                    _itemsLoadingError.value = false
                    val searchedList = response.data!!.toMutableList()
                    _searchedIVs.value = searchedList
                }else{
                    _itemsLoadingError.value = true
                }
            }
        }
        _searchedIVs.value?.remove(loadingItem)
        _searchedIVs.value = _searchedIVs.value
    }




    fun getIVs(year: Int) {
        viewModelScope.launch {
            _ivs.value?.add(loadingItem)
            _ivs.value = _ivs.value
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
            _ivs.value?.remove(loadingItem)
            _ivs.value = _ivs.value
        }
    }


    fun saveFavourite(data: DomainIvItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repoImpl.saveToFavourites(data)
        }
    }

    private fun addPageOfItems(items: List<DomainIvItem>) {
        favs.forEach{ fav ->
            items.find { it.nasaId == fav.nasaId }.apply { this?.favourite = fav.isFavourite }
        }
        _ivs.value?.addAll(items)
    }



}