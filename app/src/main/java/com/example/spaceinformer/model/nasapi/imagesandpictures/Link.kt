package com.example.spaceinformer.model.nasapi.imagesandpictures

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Link {
    @SerializedName("prompt")
    @Expose
    var prompt: String? = null

    @SerializedName("href")
    @Expose
    var href: String? = null

    @SerializedName("rel")
    @Expose
    var rel: String? = null
}