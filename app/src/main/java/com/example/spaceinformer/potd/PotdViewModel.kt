package com.example.spaceinformer.potd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.spaceinformer.model.Potd
import com.example.spaceinformer.network.PotdService
import com.example.spaceinformer.repository.PotdRepository
import com.example.spaceinformer.repository.PotdRetrofitRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class PotdViewModel(): ViewModel() {

    val repo: PotdRepository = PotdRetrofitRepository()


    val potd: LiveData<Potd> = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        val data = repo.getPotd()
        emit(data)
    }

}