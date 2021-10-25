package com.example.spaceinformer.model.appmodels

data class DomainIvItem(
    val mediaLink: String,
    val description: String,
    val mediaType: String,
    val keywords: List<String>,
    val dateCreated: String,
    val center: String,
    val photographer: String,
    val title: String,
    val album: List<String>,
    val nasaId: String,
    val location: String,
    var favourite: Boolean,
    val imageThumbnail: String
)
