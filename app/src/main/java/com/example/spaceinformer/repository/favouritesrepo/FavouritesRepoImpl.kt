package com.example.spaceinformer.repository.favouritesrepo

import com.example.spaceinformer.nasapi.imagesandpictures.Data
import com.example.spaceinformer.network.NasaIVEndpointService
import com.example.spaceinformer.room.FavouritesDao
import com.example.spaceinformer.room.entities.DataEntity
import javax.inject.Inject

class FavouritesRepoImpl
@Inject constructor(private val nasaRoomDao: FavouritesDao): FavouritesRepo {

    override suspend fun saveToFavourites(data: Data) {
        val dataEntity = DataEntity(data.nasaId!!, isFavourite = data.favourite)
        nasaRoomDao.insertOrUpdateFavourite(dataEntity)
    }

    override suspend fun isFavourite(nasaId: String): Boolean {
        return if (nasaRoomDao.doesDataExist(nasaId)){
            val dataEntity = nasaRoomDao.getFavouriteWithId(nasaId)
            dataEntity.isFavourite
        }else{
            false
        }
    }
}