package com.example.spaceinformer.repository

import com.example.spaceinformer.model.Potd
import com.example.spaceinformer.network.PotdService
import retrofit2.Retrofit
import javax.inject.Inject


class PotdRetrofitRepository @Inject constructor(val retrofit: PotdService): PotdRepository{

    override suspend fun getPotd(): Potd = retrofit.getPotd()
}