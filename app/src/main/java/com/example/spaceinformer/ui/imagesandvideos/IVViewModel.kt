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
import javax.inject.Inject

@HiltViewModel
class IVViewModel
@Inject constructor(private val retrofitRepo: IVRetrofitRepository,
                    private val roomFavouritesRepo: FavouritesRepo): ViewModel() {

    //TODO: List for recyclerView doesn't retain data, It'd be good idea to move it here
    val mediatorLiveData = MediatorLiveData<RepositoryResponse<List<IvItem>>>()

    init {
        mediatorLiveData.addSource(getIVsFromYear(2021, 1)){

        }
    }

    fun getIVs(year: Int, page: Int = 1): LiveData<RepositoryResponse<List<IvItem>>>{
        viewModelScope.launch {
            val response = retrofitRepo.getIVFromYearDistinct(year, page)
            response.status = RepositoryResponse.Status.LOADING
            
        }
    }

    fun getIVsFromYear(year: Int, page: Int = 1): LiveData<RepositoryResponse<List<IvItem>>> {
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val data = retrofitRepo.getIVFromYearDistinct(year, page)
            this.emit(data)
        }
    }

    fun saveFavourite(data: Data){
        viewModelScope.launch(Dispatchers.IO){
            roomFavouritesRepo.saveToFavourites(data)
        }
    }

    private fun attachPage(page1: LiveData<RepositoryResponse<List<IvItem>>>,
            page2: LiveData<RepositoryResponse<List<IvItem>>>): RepositoryResponse<List<IvItem>>{
        val firstPage = page1.value
        val secondPage = page2.value
        if (firstPage == null || secondPage == null){
            return RepositoryResponse.loading(null)
        }
        if (firstPage.status == RepositoryResponse.Status.ERROR ||
            secondPage.status == RepositoryResponse.Status.ERROR){
            return RepositoryResponse.error(firstPage.message + " " + secondPage.message)
        }
        val mergedList = mutableListOf<IvItem>()
        mergedList.addAll(firstPage.data!!)
        mergedList.addAll(secondPage.data!!)
        return RepositoryResponse.success(mergedList)

    }

}