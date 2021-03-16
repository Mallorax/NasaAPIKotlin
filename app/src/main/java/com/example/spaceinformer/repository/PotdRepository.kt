package com.example.spaceinformer.repository

import com.example.spaceinformer.model.Potd

interface PotdRepository {
    suspend fun getPotd(): Potd
}