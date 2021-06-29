package com.example.spaceinformer.nasapi.imagesandpictures

data class Data(
    val album: List<String>,
    val center: String,
    val date_created: String,
    val description: String,
    val keywords: List<String>,
    val location: String,
    val media_type: String,
    val nasa_id: String,
    val photographer: String,
    val title: String
)