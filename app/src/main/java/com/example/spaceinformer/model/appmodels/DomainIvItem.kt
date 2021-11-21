package com.example.spaceinformer.model.appmodels

data class DomainIvItem(
    val mediaLinks: List<String> = listOf(),
    val description: String = "",
    val mediaType: String = "",
    val keywords: List<String> = mutableListOf(),
    val dateCreated: String = "",
    val center: String = "",
    val photographer: String = "",
    val title: String = "",
    val album: List<String> = mutableListOf(),
    val nasaId: String = "",
    val location: String = "",
    var favourite: Boolean = false,
    val imageThumbnail: String = ""
){
    fun searchForMobileVideo(): String?{
        return mediaLinks[4]
    }
}
