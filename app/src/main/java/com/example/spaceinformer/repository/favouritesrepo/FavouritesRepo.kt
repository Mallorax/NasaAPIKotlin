package com.example.spaceinformer.repository.favouritesrepo

import com.example.spaceinformer.nasapi.imagesandpictures.Data

interface FavouritesRepo {

    suspend fun saveToFavourites(data: Data)
    suspend fun isFavourite(nasaId: String):Boolean
}