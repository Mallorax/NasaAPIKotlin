package com.example.spaceinformer.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class DataEntity(

    @PrimaryKey
    @ColumnInfo(name = "nasa_id")
    var nasaId: String,

    var isFavourite: Boolean
)