package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.nasapi.imagesandpictures.IvItem


interface IVRepository {
    suspend fun getIVFromYearDistinct(year: Int, page: Int = 1): List<IvItem>
    suspend fun getIVWithNasaId(id: String): IvItem
}