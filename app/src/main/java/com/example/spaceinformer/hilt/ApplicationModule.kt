package com.example.spaceinformer.hilt

import android.content.Context
import androidx.room.Room
import com.example.spaceinformer.network.NasaIVEndpointService
import com.example.spaceinformer.network.NasaPotdService
import com.example.spaceinformer.room.FavouritesDao
import com.example.spaceinformer.room.RoomNasaDatabase
import com.example.spaceinformer.ui.imagesandvideos.LocalCacheDataSourceFactory
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun providePotdRetrofitEndpoint(): NasaPotdService {
        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/planetary/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NasaPotdService::class.java)
    }




    @Provides
    @Singleton
    fun provideNasaAPIIVEndpoint(): NasaIVEndpointService {
        return Retrofit.Builder()
            .baseUrl("https://images-api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NasaIVEndpointService::class.java)
    }

    @Provides
    @Singleton
    fun providesRoomNasaDatabase(@ApplicationContext app: Context): RoomNasaDatabase =
        Room.databaseBuilder(app, RoomNasaDatabase::class.java, "nasa.db").fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNasaDbDao(db: RoomNasaDatabase) = db.nasaDao()
}