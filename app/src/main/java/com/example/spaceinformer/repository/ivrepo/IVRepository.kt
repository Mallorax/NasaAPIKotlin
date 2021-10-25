package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.model.nasapi.imagesandpictures.Data
import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.repository.RepositoryResponse
import kotlinx.coroutines.flow.Flow

//TODO: Should be merged with favourites repository
interface IVRepository {
    suspend fun getIVFromYearDistinct(year: Int, page: Int = 1): RepositoryResponse<List<IvItem>>
    suspend fun getIVWithNasaId(id: String): RepositoryResponse<IvItem>
    suspend fun saveToFavourites(data: Data)
    fun isFavourite(nasaId: String): Flow<Boolean>
    fun getAllFavourites(): Flow<List<String>>
}