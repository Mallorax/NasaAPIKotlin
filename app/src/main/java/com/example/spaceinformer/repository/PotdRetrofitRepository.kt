package com.example.spaceinformer.repository

import com.example.spaceinformer.model.NasaAPI.Potd.Potd
import com.example.spaceinformer.network.NasaPotdService
import javax.inject.Inject

class PotdRetrofitRepository @Inject constructor(private val retrofit: NasaPotdService): PotdRepository{

    override suspend fun getPotd(): Potd = retrofit.getPotd()
}