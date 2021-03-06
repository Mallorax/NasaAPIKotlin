package com.example.spaceinformer.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spaceinformer.model.entities.DataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface FavouritesDao {
    @Query("SELECT * FROM data WHERE ` is_favourite` == 1")
    fun loadFavourites(): Flow<List<DataEntity>?>

    fun getFavouritesDistinct() = loadFavourites().distinctUntilChanged()

    @Query("SELECT * FROM data")
    fun getEntireTableOfFavourites(): Flow<List<DataEntity>>

    fun getEntireTableOfFavouritesDistinct() = getEntireTableOfFavourites().distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateFavourite(favourite: DataEntity)

    @Query("SELECT * FROM data WHERE nasa_id = :id")
    suspend fun isFavourite(id: String): DataEntity?

    @Query("SELECT EXISTS(SELECT * FROM data WHERE nasa_id = :id)")
    fun doesDataExist(id: String):Flow<Boolean?>
}