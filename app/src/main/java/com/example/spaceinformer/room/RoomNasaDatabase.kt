package com.example.spaceinformer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spaceinformer.room.entities.DataEntity

@Database(entities = arrayOf(DataEntity::class), version = 1)
abstract class RoomNasaDatabase: RoomDatabase() {
    abstract fun nasaDao(): FavouritesDao
}