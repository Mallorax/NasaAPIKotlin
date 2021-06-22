package com.example.spaceinformer.network

import com.example.spaceinformer.model.NasaAPI.get.Collection
import retrofit2.http.GET

interface GetEndpointService {

    @GET
    suspend fun getInfoWithStartYear(year: String): Collection
}