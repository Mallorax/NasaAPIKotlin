package com.example.spaceinformer.model.appmodels


data class PictureOfTheDay(
    val resource: String,
    val conceptTags: String,
    val title: String,
    val date: String,
    val url: String,
    val hdurl: String,
    val mediaType: String,
    val explanation: String,
    val concepts: String,
    val thumbnailUrl: String,
    val copyright: String,
    val serviceVersion: String,
    var isFavourite: Boolean = false)
