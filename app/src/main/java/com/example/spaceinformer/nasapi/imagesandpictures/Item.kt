package com.example.spaceinformer.nasapi.imagesandpictures

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {
    @SerializedName("href")
    @Expose
    var href: String? = null

    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    @SerializedName("links")
    @Expose
    var links: List<Link__1>? = null
}