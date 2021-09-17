package com.example.spaceinformer.network

import com.example.spaceinformer.nasapi.imagesandpictures.IVResponsePojo
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaIVEndpointService {

    @GET("search")
    suspend fun getIVFromYear(@Query("year_start")year: String): IVResponsePojo

    @GET("search")
    suspend fun getIVWithId(@Query("nasa_id")nasaId: String): IVResponsePojo
}