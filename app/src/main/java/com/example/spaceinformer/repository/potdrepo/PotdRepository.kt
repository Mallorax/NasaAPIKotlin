package com.example.spaceinformer.repository.potdrepo

import com.example.spaceinformer.nasapi.potd.Potd

interface PotdRepository {
    suspend fun getPotd(): Potd
}