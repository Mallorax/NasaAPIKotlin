package com.example.spaceinformer.repository

import com.example.spaceinformer.nasapi.tmp.Item


interface IVRepository {
    suspend fun getIVFromYear(year: Int): List<Item>
}