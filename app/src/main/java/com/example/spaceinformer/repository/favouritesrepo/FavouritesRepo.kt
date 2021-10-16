package com.example.spaceinformer.repository.favouritesrepo

import com.example.spaceinformer.nasapi.imagesandpictures.Data
import com.example.spaceinformer.room.entities.DataEntity
import kotlinx.coroutines.flow.Flow

interface FavouritesRepo {

    suspend fun saveToFavourites(data: Data)
    suspend fun isFavourite(nasaId: String):Boolean
    suspend fun getAllFavourites(): Flow<List<String>>
}