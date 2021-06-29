package com.example.spaceinformer.model.NasaAPI.potd

import com.google.gson.annotations.SerializedName

data class Potd(
    val resource: String,
    @SerializedName("concept_tags")
    val conceptTags: String,
    val title: String,
    val date: String,
    val url: String,
    val hdurl: String,
    @SerializedName("media_type")
    val mediaType: String,
    val explanation: String,
    val concepts: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    val copyright: String,
    @SerializedName("service_version")
    val serviceVersion: String)    {
}