package com.example.spaceinformer.repository

import com.example.spaceinformer.nasapi.potd.Potd

interface PotdRepository {
    suspend fun getPotd(): Potd
}