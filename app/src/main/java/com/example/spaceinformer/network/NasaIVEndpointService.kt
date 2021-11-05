package com.example.spaceinformer.network

import com.example.spaceinformer.model.nasapi.imagesandpictures.IVResponsePojo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaIVEndpointService {

    //For now only images are supported
    @GET("search")
    suspend fun getIVFromYear(@Query("year_start")year: String,
                              @Query("page")page: Int = 1,
                              @Query("media_type")mediaType: String = "image"): Response<IVResponsePojo>

    @GET("search")
    suspend fun getIVWithId(@Query("nasa_id")nasaId: String): Response<IVResponsePojo>


    @GET("search")
    suspend fun getWithFreeText(@Query("q")text: String,
                                @Query("page")page: Int = 1): Response<IVResponsePojo>




}