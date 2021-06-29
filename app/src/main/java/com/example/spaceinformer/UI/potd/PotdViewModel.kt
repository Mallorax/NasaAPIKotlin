package com.example.spaceinformer.UI.potd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.model.NasaAPI.Potd.Potd
import com.example.spaceinformer.repository.PotdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PotdViewModel @Inject constructor(private val repo: PotdRepository): ViewModel() {

    val potd: LiveData<Potd> = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        val data = repo.getPotd()
        emit(data)
    }

}