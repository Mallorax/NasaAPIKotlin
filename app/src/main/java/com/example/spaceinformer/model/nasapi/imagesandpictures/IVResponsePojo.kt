package com.example.spaceinformer.model.nasapi.imagesandpictures

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IVResponsePojo {
    @SerializedName("collection")
    @Expose
    var ivDataCollection: IvDataCollection? = null
}