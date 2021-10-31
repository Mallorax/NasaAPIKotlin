package com.example.spaceinformer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spaceinformer.model.entities.DataEntity

@Database(entities = arrayOf(DataEntity::class), version = 4)
abstract class RoomNasaDatabase: RoomDatabase() {
    abstract fun nasaDao(): FavouritesDao
}