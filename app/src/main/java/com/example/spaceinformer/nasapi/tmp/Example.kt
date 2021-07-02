package com.example.spaceinformer.nasapi.tmp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Example {
    @SerializedName("collection")
    @Expose
    var collection: Collection? = null
}