package com.example.spaceinformer.network

import com.example.spaceinformer.application.Constants
import com.example.spaceinformer.model.NasaAPI.Potd.Potd
import retrofit2.http.GET

interface PotdService {

    @GET("apod?api_key=${Constants.API_KEY}")
    suspend fun getPotd(): Potd
}