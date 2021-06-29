package com.example.spaceinformer.repository

import com.example.spaceinformer.model.NasaAPI.get.Item
import java.time.Year

interface IVRepository {
    suspend fun getIVFromYear(year: Int): List<Item>
}