package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.network.NasaIVEndpointService
import javax.inject.Inject

class IVRetrofitRepository @Inject constructor(private val retrofit: NasaIVEndpointService):
    IVRepository {

    //Returns images and videos from given year, but only 1 image from single nasa event
    override suspend fun getIVFromYearDistinct(year: Int): List<IvItem> {
        val yearString = year.toString()
        val responseBody = retrofit.getIVFromYear(yearString).ivDataCollection
        responseBody?.apply{
            this.ivItems = this.ivItems?.distinctBy { t ->
                t.data?.first()?.title
            }
        }
        return responseBody?.ivItems!!
    }

}