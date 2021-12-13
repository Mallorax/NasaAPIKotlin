package com.example.spaceinformer.repository.potdrepo

import com.example.spaceinformer.model.appmodels.PictureOfTheDay
import com.example.spaceinformer.repository.RepositoryResponse

interface PotdRepository {
    suspend fun getPotd(): RepositoryResponse<PictureOfTheDay>
}