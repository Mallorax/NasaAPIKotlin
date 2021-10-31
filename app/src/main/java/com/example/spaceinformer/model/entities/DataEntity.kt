package com.example.spaceinformer.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class DataEntity(

    @PrimaryKey
    @ColumnInfo(name = "nasa_id")
    var nasaId: String,

    @ColumnInfo(name = " is_favourite")
    var isFavourite: Boolean
)