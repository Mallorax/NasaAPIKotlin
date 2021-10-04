package com.example.spaceinformer.ui.imagesandvideos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.nasapi.imagesandpictures.Data
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
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

    fun getIVsFromYear(year: Int, page: Int = 1): LiveData<List<IvItem>> {
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

}