package com.example.spaceinformer.nasapi.tmp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Metadata {
    @SerializedName("total_hits")
    @Expose
    var totalHits: Int? = null
}