package com.example.spaceinformer.model

import com.example.spaceinformer.model.appmodels.AppIvItem
import com.example.spaceinformer.model.nasapi.imagesandpictures.Data
import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem

fun mapIvItemNetwork(input: Data): AppIvItem{
    return AppIvItem(
        input.mediaType.orEmpty(),
        input.description.orEmpty(),
        input.mediaType.orEmpty(),
        input.keywords.orEmpty(),
        input.dateCreated.orEmpty(),
        input.center.orEmpty(),
        input.photographer.orEmpty(),
        input.title.orEmpty(),
        input.album.orEmpty(),
        input.nasaId.orEmpty(),
        input.location.orEmpty(),
        input.favourite
    )
}