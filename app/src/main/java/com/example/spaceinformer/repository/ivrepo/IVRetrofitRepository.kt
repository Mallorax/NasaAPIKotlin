package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.nasapi.imagesandpictures.IVResponsePojo
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.network.NasaIVEndpointService
import com.example.spaceinformer.repository.BaseDataSource
import com.example.spaceinformer.repository.RepositoryResponse
import retrofit2.Response
import javax.inject.Inject

class IVRetrofitRepository @Inject constructor(private val retrofit: NasaIVEndpointService): BaseDataSource(),
    IVRepository {

    //Returns images and videos from given year, but only 1 image from single nasa event
    override suspend fun getIVFromYearDistinct(year: Int, page: Int): RepositoryResponse<List<IvItem>> {
        val yearString = year.toString()
        val response = getResult {
            retrofit.getIVFromYear(yearString, page)
        }
        val result = response.data?.ivDataCollection?.ivItems
        return RepositoryResponse(response.status, result, response.message)
    }

    override suspend fun getIVWithNasaId(id: String): RepositoryResponse<IvItem> {
        val response = getResult { retrofit.getIVWithId(id) }
        val result = response.data?.ivDataCollection?.ivItems?.first()
        return RepositoryResponse(response.status, result, response.message)
    }

}