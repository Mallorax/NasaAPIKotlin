package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.nasapi.imagesandpictures.IvItem


interface IVRepository {
    suspend fun getIVFromYearDistinct(year: Int): List<IvItem>
}