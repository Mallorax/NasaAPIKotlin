package com.example.spaceinformer.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.repository.ivrepo.IVRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: IVRetrofitRepository): ViewModel() {

    //TODO: For now its questionable if this viewmodel is needed
    // or if other one could be reused for details fragment, reevaluate this later in the project

    fun getSpecificIV(nasaId: String): LiveData<IvItem> {
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val data = repo.getIVWithNasaId(nasaId)
            this.emit(data)
        }
    }

}