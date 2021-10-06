package com.example.spaceinformer.ui.imagesandvideos

import androidx.lifecycle.*
import com.example.spaceinformer.nasapi.imagesandpictures.Data
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.repository.favouritesrepo.FavouritesRepo
import com.example.spaceinformer.repository.ivrepo.IVRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IVViewModel
@Inject constructor(
    private val retrofitRepo: IVRetrofitRepository,
    private val roomFavouritesRepo: FavouritesRepo
) : ViewModel() {

    //TODO: List for recyclerView doesn't retain data, It'd be good idea to move it here
    val mediatorLiveData = MediatorLiveData<RepositoryResponse<List<IvItem>>>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _ivs = MutableLiveData<RepositoryResponse<List<IvItem>>>()
    val ivs: LiveData<RepositoryResponse<List<IvItem>>> get() = _ivs


    fun getIVs(year: Int, page: Int = 1){
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO) {
                val response = retrofitRepo.getIVFromYearDistinct(year, page)
                withContext(Dispatchers.Main){
                    _ivs.value = response
                    _loading.value = false
                }
            }
        }
    }

    fun getIVsFromYear(year: Int, page: Int = 1): LiveData<RepositoryResponse<List<IvItem>>> {
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val data = retrofitRepo.getIVFromYearDistinct(year, page)
            this.emit(data)
        }
    }

    fun saveFavourite(data: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            roomFavouritesRepo.saveToFavourites(data)
        }
    }

    private fun attachPage(
        page1: LiveData<RepositoryResponse<List<IvItem>>>,
        page2: LiveData<RepositoryResponse<List<IvItem>>>
    ): RepositoryResponse<List<IvItem>> {
        val firstPage = page1.value
        val secondPage = page2.value
        if (firstPage == null || secondPage == null) {
            return RepositoryResponse.loading(null)
        }
        if (firstPage.status == RepositoryResponse.Status.ERROR ||
            secondPage.status == RepositoryResponse.Status.ERROR
        ) {
            return RepositoryResponse.error(firstPage.message + " " + secondPage.message)
        }
        val mergedList = mutableListOf<IvItem>()
        mergedList.addAll(firstPage.data!!)
        mergedList.addAll(secondPage.data!!)
        return RepositoryResponse.success(mergedList)

    }

}