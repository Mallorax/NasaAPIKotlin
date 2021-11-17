package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.model.entities.DataEntity
import com.example.spaceinformer.model.mapIvItemNetwork
import com.example.spaceinformer.model.nasapi.imagesandpictures.IVResponsePojo
import com.example.spaceinformer.network.NasaIVEndpointService
import com.example.spaceinformer.repository.BaseDataSource
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.room.FavouritesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception
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
    ): RepositoryResponse<List<DomainIvItem>> {
        val yearString = year.toString()
        val response = getResult {
            retrofit.getIVFromYear(yearString, page)
        }
        return mapIVSearchResultsToList(response)
    }

    override suspend fun getIvsBySearch(searchWords: String, page: Int): RepositoryResponse<List<DomainIvItem>> {
        val response = getResult { retrofit.getWithFreeText(searchWords, page) }
        val repoResponse = mapIVSearchResultsToList(response)

        val data = repoResponse.data
        try {
            data?.forEach { t->
                t.apply {
                    if (isFavourite(this.nasaId).data == true){
                        this.favourite = true
                    }
                }
            }
            repoResponse.data = data
        }catch (e: Exception){e.printStackTrace()}
        return repoResponse
    }


    override suspend fun getIVWithNasaId(id: String): RepositoryResponse<DomainIvItem> {
        val response = getResult { retrofit.getIVWithId(id) }
        val datasourceResponse = response.data?.ivDataCollection?.ivItems
        val result = datasourceResponse?.map{
            mapIvItemNetwork(it, getMediaLinks(it.href!!))
        }?.first()
        return RepositoryResponse(response.status, result, response.message)
    }

    override suspend fun saveToFavourites(data: DomainIvItem) {
        val dataEntity = DataEntity(data.nasaId, isFavourite = data.favourite)
        favDao.insertOrUpdateFavourite(dataEntity)
    }

    override suspend fun isFavourite(nasaId: String): RepositoryResponse<Boolean> {
        val result = favDao.isFavourite(nasaId)
        return if (result != null){
            RepositoryResponse.success(result.isFavourite)
        }else{
            RepositoryResponse.error("There is no such value in db")
        }
    }

    override fun getAllFavouritesIds(): Flow<List<String>> {
        return favDao.getFavouritesDistinct()
            .filter { t ->
                t!!.all {
                it.isFavourite
            } }
            .map { it!!.map { t-> t.nasaId } }
            .flowOn(Dispatchers.IO)

    }

    override fun getEveryItemInFavourites(): Flow<List<DataEntity>> {
        return favDao.getEntireTableOfFavouritesDistinct()
            .flowOn(Dispatchers.IO)
    }

    override fun getAllFavourites(): Flow<List<DataEntity>> {
        return favDao.getFavouritesDistinct()
            .filterNotNull()
            .flowOn(Dispatchers.IO)

    }

    private suspend fun  mapIVSearchResultsToList(ivResponse: RepositoryResponse<IVResponsePojo>): RepositoryResponse<List<DomainIvItem>> {
        val dataList = ivResponse.data?.ivDataCollection?.ivItems
        val result: List<DomainIvItem>

        return try {
            result = dataList?.map { t-> mapIvItemNetwork(t, getMediaLinks(t.href!!))}!!
            RepositoryResponse(ivResponse.status, result, ivResponse.message)
        }catch (e: Exception){
            RepositoryResponse.error(e.message.orEmpty())
        }
    }

    private suspend fun getMediaLinks(href: String): List<String>{
        return getResult { retrofit.getMediaLinks(href) }.data!!
    }


}