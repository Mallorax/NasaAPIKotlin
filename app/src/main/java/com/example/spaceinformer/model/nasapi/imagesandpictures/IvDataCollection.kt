package com.example.spaceinformer.model.nasapi.imagesandpictures

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IvDataCollection {
    @SerializedName("metadata")
    @Expose
    var metadata: Metadata? = null

    @SerializedName("version")
    @Expose
    var version: String? = null

    @SerializedName("href")
    @Expose
    var href: String? = null

    @SerializedName("links")
    @Expose
    var links: List<Link>? = null

    @SerializedName("items")
    @Expose
    var ivItems: List<IvItem>? = null
}