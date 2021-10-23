package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.repository.RepositoryResponse


interface IVRepository {
    suspend fun getIVFromYearDistinct(year: Int, page: Int = 1): RepositoryResponse<List<IvItem>>
    suspend fun getIVWithNasaId(id: String): RepositoryResponse<IvItem>
}