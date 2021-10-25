package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.model.entities.DataEntity
import com.example.spaceinformer.repository.RepositoryResponse
import kotlinx.coroutines.flow.Flow

//TODO: Should be merged with favourites repository
interface IVRepository {
    suspend fun getIVFromYearDistinct(year: Int, page: Int = 1): RepositoryResponse<List<DomainIvItem>>
    suspend fun getIVWithNasaId(id: String): RepositoryResponse<com.example.spaceinformer.model.nasapi.imagesandpictures.AppIvItem>
    suspend fun saveToFavourites(data: DomainIvItem)
    suspend fun isFavourite(nasaId: String): RepositoryResponse<Boolean>
    fun getAllFavourites(): Flow<List<DataEntity>>
    fun getAllFavouritesIds(): Flow<List<String>>
}