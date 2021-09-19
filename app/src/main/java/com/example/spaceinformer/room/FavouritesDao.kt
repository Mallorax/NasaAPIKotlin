package com.example.spaceinformer.room

import androidx.room.Dao
import androidx.room.Query
import com.example.spaceinformer.room.Entities.DataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {
    @Query("SELECT * FROM data WHERE isFavourite = 1")
    fun loadFavourites(): Flow<List<DataEntity>>
}