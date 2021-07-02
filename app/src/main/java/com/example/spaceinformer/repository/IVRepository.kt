package com.example.spaceinformer.repository

import com.example.spaceinformer.nasapi.imagesandpictures.Item


interface IVRepository {
    suspend fun getIVFromYear(year: Int): List<Item>
}