package com.example.spaceinformer.repository.potdrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.spaceinformer.nasapi.potd.Potd
import com.example.spaceinformer.network.NasaPotdService
import retrofit2.Response
import javax.inject.Inject

class PotdRetrofitRepository @Inject constructor(private val retrofit: NasaPotdService):
    PotdRepository {

    override suspend fun getPotd(): Response<Potd> = retrofit.getPotd()
}