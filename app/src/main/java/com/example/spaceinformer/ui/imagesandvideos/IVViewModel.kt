package com.example.spaceinformer.ui.imagesandvideos

import androidx.lifecycle.*
import com.example.spaceinformer.model.nasapi.imagesandpictures.Data
import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.repository.favouritesrepo.FavouritesRepo
import com.example.spaceinformer.repository.ivrepo.IVRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IVViewModel
@Inject constructor(
    private val repoImpl: IVRepositoryImpl,
    private val roomFavouritesRepo: FavouritesRepo
) : ViewModel() {

    //TODO: Add more transparency to errors

    private val _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus: LiveData<Boolean> get() = _loadingStatus

    private val _ivs = MutableLiveData<MutableList<IvItem>>()
    val ivs: LiveData<MutableList<IvItem>> get() = _ivs

    private val _itemsLoadingError = MutableLiveData<Boolean>()
    val itemsLoadingError: LiveData<Boolean> get() = _itemsLoadingError

    private var page = 1

    init {
        _ivs.value = mutableListOf<IvItem>()
    }


    private fun addPageOfItems(items: List<IvItem>){
        _ivs.value?.addAll(items)
        _ivs.value = _ivs.value
    }


    fun getIVs(year: Int){
        viewModelScope.launch {
            _loadingStatus.value = true
            withContext(Dispatchers.IO) {
                val response = repoImpl.getIVFromYearDistinct(year, page)
                page++
                withContext(Dispatchers.Main){
                    if (response.status != RepositoryResponse.Status.ERROR && response.data != null){
                        _itemsLoadingError.value = false
                        addPageOfItems(response.data!!)
                    }else{
                        _itemsLoadingError.value = true
                    }
                }
            }
            _loadingStatus.value = false
        }
    }


    fun saveFavourite(data: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            roomFavouritesRepo.saveToFavourites(data)
        }
    }


}