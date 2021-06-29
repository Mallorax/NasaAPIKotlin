package com.example.spaceinformer.network

import com.example.spaceinformer.nasapi.imagesandpictures.Collection
import retrofit2.http.GET
import retrofit2.http.Path

interface NasaIVEndpointService {

    @GET("search?year_start={year}&year_end={year}")
    suspend fun getIVFromYear(@Path("year")year: String): Collection
}