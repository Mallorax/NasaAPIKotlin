package com.example.spaceinformer.model.nasapi.imagesandpictures

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IvItem {
    @SerializedName("href")
    @Expose
    var href: String? = null

    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    @SerializedName("links")
    @Expose
    var imageLinks: List<ImageLink>? = null
}