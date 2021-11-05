package com.example.spaceinformer.repository.potdrepo

import com.example.spaceinformer.model.appmodels.PictureOfTheDay
import com.example.spaceinformer.model.mapPicOfTheDay
import com.example.spaceinformer.network.NasaPotdService
import com.example.spaceinformer.repository.BaseDataSource
import com.example.spaceinformer.repository.RepositoryResponse
import javax.inject.Inject

class PotdRetrofitRepository @Inject constructor(private val retrofit: NasaPotdService): BaseDataSource(),
    PotdRepository {

    override suspend fun getPotd(): RepositoryResponse<PictureOfTheDay> {
        val response = getResult{ retrofit.getPotd()}
        val data = response.data
        val result  = mapPicOfTheDay(data)
        return RepositoryResponse(response.status, result, response.message)
    }
}