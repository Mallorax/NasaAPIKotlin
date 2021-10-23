package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.network.NasaIVEndpointService
import com.example.spaceinformer.repository.BaseDataSource
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.room.FavouritesDao
import javax.inject.Inject

class IVRepositoryImpl @Inject constructor(
    private val retrofit: NasaIVEndpointService,
    private val favDao: FavouritesDao
) : BaseDataSource(),
    IVRepository {

    //Returns images and videos from given year, but only 1 image from single nasa event
    override suspend fun getIVFromYearDistinct(
        year: Int,
        page: Int
    ): RepositoryResponse<List<IvItem>> {
        val yearString = year.toString()
        val response = getResult {
            retrofit.getIVFromYear(yearString, page)
        }
        if (response.status == RepositoryResponse.Status.SUCCESS) {
            response.data?.ivDataCollection?.ivItems?.apply {
                this.forEach{
                    if (favDao.doesDataExist(it.data?.first()?.nasaId!!)){
                        it.data?.first()?.favourite = favDao.getFavouriteWithId(it.data?.first()?.nasaId!!).isFavourite
                    }
                }
            }
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