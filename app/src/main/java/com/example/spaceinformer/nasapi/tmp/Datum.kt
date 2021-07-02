package com.example.spaceinformer.nasapi.tmp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {
    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("media_type")
    @Expose
    var mediaType: String? = null

    @SerializedName("keywords")
    @Expose
    var keywords: List<String>? = null

    @SerializedName("date_created")
    @Expose
    var dateCreated: String? = null

    @SerializedName("center")
    @Expose
    var center: String? = null

    @SerializedName("photographer")
    @Expose
    var photographer: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("album")
    @Expose
    var album: List<String>? = null

    @SerializedName("nasa_id")
    @Expose
    var nasaId: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null
}