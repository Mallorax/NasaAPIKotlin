package com.example.spaceinformer.repository

import com.example.spaceinformer.nasapi.tmp.Item
import com.example.spaceinformer.network.NasaIVEndpointService
import javax.inject.Inject

class IVRetrofitRepository @Inject constructor(private val retrofit: NasaIVEndpointService): IVRepository {

    override suspend fun getIVFromYear(year: Int): List<Item> {
        val yearString = year.toString()
        return retrofit.getIVFromYear(yearString).items!! //TODO clean up
    }

}