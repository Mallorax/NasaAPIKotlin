package com.example.spaceinformer.repository

import com.example.spaceinformer.model.NasaAPI.potd.Potd

interface PotdRepository {
    suspend fun getPotd(): Potd
}