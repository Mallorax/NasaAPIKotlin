package com.example.spaceinformer.model

import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.model.nasapi.imagesandpictures.Data

fun mapIvItemNetwork(input: Data): DomainIvItem{
    return DomainIvItem(
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