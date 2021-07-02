package com.example.spaceinformer.nasapi.tmp

import com.example.spaceinformer.nasapi.imagesandpictures.Link
import com.example.spaceinformer.nasapi.imagesandpictures.Metadata
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Collection {
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
    var items: List<Item>? = null
}