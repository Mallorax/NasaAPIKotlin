package com.example.spaceinformer.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spaceinformer.room.entities.DataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface FavouritesDao {
    @Query("SELECT * FROM data WHERE isFavourite == 1")
    fun loadFavourites(): Flow<List<DataEntity>>

    fun getFavouritesDistinct() = loadFavourites().distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateFavourite(favourite: DataEntity)

    @Query("SELECT * FROM data WHERE nasa_id = :id")
    suspend fun getFavouriteWithId(id: String): DataEntity

    @Query("SELECT EXISTS(SELECT * FROM data WHERE nasa_id = :id)")
    suspend fun doesDataExist(id: String):Boolean
}