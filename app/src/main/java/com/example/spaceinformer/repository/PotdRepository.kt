package com.example.spaceinformer.repository

import com.example.spaceinformer.model.NasaAPI.Potd.Potd

interface PotdRepository {
    suspend fun getPotd(): Potd
}