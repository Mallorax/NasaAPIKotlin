package com.example.spaceinformer.repository.ivrepo

import com.example.spaceinformer.model.appmodels.AppIvItem
import com.example.spaceinformer.model.entities.DataEntity
import com.example.spaceinformer.model.mapIvItemNetwork
import com.example.spaceinformer.model.nasapi.imagesandpictures.Data
import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem
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
    ): RepositoryResponse<List<IvItem>> {
        val yearString = year.toString()
        val response = getResult {
            retrofit.getIVFromYear(yearString, page)
        }
        val datasourceResponse = response.data?.ivDataCollection?.ivItems
        var result: List<AppIvItem>

        try {
            result = datasourceResponse?.flatMap{
                it.data!!.map { t-> mapIvItemNetwork(t) }
            }!!
            getAllFavourites().collect { favs->
                favs.forEach { fav ->
                    result.find { item ->
                        item.nasaId == fav.nasaId && fav.isFavourite
                    }.let {
                        if (it != null){
                            it.favourite = fav.isFavourite
                        }
                    }
                }
            }
            return RepositoryResponse(response.status, datasourceResponse, response.message)
        }catch (e: Exception){
            return RepositoryResponse.error(e.message.orEmpty())
        }

    }

    override suspend fun getIVWithNasaId(id: String): RepositoryResponse<IvItem> {
        val response = getResult { retrofit.getIVWithId(id) }
        val result = response.data?.ivDataCollection?.ivItems?.first()
        return RepositoryResponse(response.status, result, response.message)
    }

    override suspend fun saveToFavourites(data: Data) {
        val dataEntity = DataEntity(data.nasaId!!, isFavourite = data.favourite)
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
    override fun getAllFavourites(): Flow<List<DataEntity>> {
        return favDao.getFavouritesDistinct()
            .filterNotNull()
            .flowOn(Dispatchers.IO)

    }

}