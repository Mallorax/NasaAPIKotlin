package com.example.spaceinformer.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spaceinformer.room.Entities.DataEntity

@Database(entities = arrayOf(DataEntity::class), version = 1)
abstract class RoomNasaDatabase: RoomDatabase() {

    companion object {
        private val DB_NAME = "nasa_db"
        @Volatile
        private lateinit var INSTANCE: RoomNasaDatabase

        fun getInstance(context: Context): RoomNasaDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomNasaDatabase::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun nasaDao(): FavouritesDao
}