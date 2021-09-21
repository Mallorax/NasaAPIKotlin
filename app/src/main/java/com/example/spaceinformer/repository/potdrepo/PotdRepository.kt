package com.example.spaceinformer.repository.potdrepo

import androidx.lifecycle.LiveData
import com.example.spaceinformer.nasapi.potd.Potd
import retrofit2.Response

interface PotdRepository {
    suspend fun getPotd(): Response<Potd>
}