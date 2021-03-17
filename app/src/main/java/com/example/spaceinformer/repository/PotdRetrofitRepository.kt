package com.example.spaceinformer.repository

import com.example.spaceinformer.model.Potd
import com.example.spaceinformer.network.PotdService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class PotdRetrofitRepository (): PotdRepository{

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/planetary/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(PotdService::class.java)

    override suspend fun getPotd(): Potd = retrofit.getPotd()
}