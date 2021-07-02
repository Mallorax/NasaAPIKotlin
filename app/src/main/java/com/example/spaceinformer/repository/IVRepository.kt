package com.example.spaceinformer.repository

import com.example.spaceinformer.nasapi.imagesandpictures.IvItem


interface IVRepository {
    suspend fun getIVFromYearDistinct(year: Int): List<IvItem>
}