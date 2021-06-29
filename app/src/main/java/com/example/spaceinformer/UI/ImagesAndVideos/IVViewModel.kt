package com.example.spaceinformer.UI.ImagesAndVideos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.model.NasaAPI.get.Collection
import com.example.spaceinformer.model.NasaAPI.get.Item
import com.example.spaceinformer.repository.IVRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class IVViewModel @Inject constructor(private val repo: IVRetrofitRepository): ViewModel() {

    fun getIVsFromYear(year: Int): LiveData<List<Item>>{
        val ivs: LiveData<List<Item>> = liveData (context = viewModelScope.coroutineContext + Dispatchers.IO){
            val data = repo.getIVFromYear(year)
            emit(data)
        }
        return ivs
    }
}