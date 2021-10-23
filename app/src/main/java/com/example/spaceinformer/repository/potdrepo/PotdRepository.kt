package com.example.spaceinformer.repository.potdrepo

import com.example.spaceinformer.model.nasapi.potd.Potd
import retrofit2.Response

interface PotdRepository {
    suspend fun getPotd(): Response<Potd>
}