package com.example.spaceinformer.dagger

import com.example.spaceinformer.network.PotdService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providePotdRetrofitService(): PotdService{
        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/planetary/apod")
            .build()
            .create(PotdService::class.java)
    }

}