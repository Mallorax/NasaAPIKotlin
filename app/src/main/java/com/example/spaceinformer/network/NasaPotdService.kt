package com.example.spaceinformer.network

import com.example.spaceinformer.application.Constants
import com.example.spaceinformer.model.nasapi.potd.Potd
import retrofit2.Response
import retrofit2.http.GET

interface NasaPotdService {

    @GET("apod?api_key=${Constants.API_KEY}")
    suspend fun getPotd(): Response<Potd>
}