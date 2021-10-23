package com.example.spaceinformer.repository.favouritesrepo

import com.example.spaceinformer.model.nasapi.imagesandpictures.Data
import kotlinx.coroutines.flow.Flow

interface FavouritesRepo {

    suspend fun saveToFavourites(data: Data)
    suspend fun isFavourite(nasaId: String):Boolean
    suspend fun getAllFavourites(): Flow<List<String>>
}