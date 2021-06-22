package com.example.spaceinformer.repository

import com.example.spaceinformer.model.NasaAPI.Potd.Potd
import com.example.spaceinformer.network.PotdService
import javax.inject.Inject

class PotdRetrofitRepository @Inject constructor(private val retrofit: PotdService): PotdRepository{

    override suspend fun getPotd(): Potd = retrofit.getPotd()
}