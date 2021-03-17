package com.example.spaceinformer.repository

import com.example.spaceinformer.model.Potd
import com.example.spaceinformer.network.PotdService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class PotdRetrofitRepository @Inject constructor(private val retrofit: PotdService): PotdRepository{

    override suspend fun getPotd(): Potd = retrofit.getPotd()
}