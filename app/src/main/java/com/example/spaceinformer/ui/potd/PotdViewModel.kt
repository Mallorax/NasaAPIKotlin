package com.example.spaceinformer.ui.potd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.nasapi.potd.Potd
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