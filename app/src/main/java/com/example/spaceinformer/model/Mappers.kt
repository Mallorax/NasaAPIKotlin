package com.example.spaceinformer.model

import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.model.appmodels.PictureOfTheDay
import com.example.spaceinformer.model.nasapi.imagesandpictures.Data
import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.model.nasapi.potd.Potd

fun mapIvItemNetwork(input: IvItem): DomainIvItem{
    return DomainIvItem(
        input.data?.first()?.mediaType.orEmpty(),
        input.data?.first()?.description.orEmpty(),
        input.data?.first()?.mediaType.orEmpty(),
        input.data?.first()?.keywords.orEmpty(),
        input.data?.first()?.dateCreated.orEmpty(),
        input.data?.first()?.center.orEmpty(),
        input.data?.first()?.photographer.orEmpty(),
        input.data?.first()?.title.orEmpty(),
        input.data?.first()?.album.orEmpty(),
        input.data?.first()?.nasaId.orEmpty(),
        input.data?.first()?.location.orEmpty(),
        input.data?.first()?.favourite == true,
        input.imageLinks?.first()?.href.orEmpty()
    )
}

fun mapPicOfTheDay(input: Potd?): PictureOfTheDay{
    return PictureOfTheDay(input?.resource.orEmpty(),
        input?.conceptTags.orEmpty(),
        input?.title.orEmpty(),
        input?.date.orEmpty(),
        input?.url.orEmpty(),
        input?.hdurl.orEmpty(),
        input?.mediaType.orEmpty(),
        input?.explanation.orEmpty(),
        input?.concepts.orEmpty(),
        input?.thumbnailUrl.orEmpty(),
        input?.copyright.orEmpty(),
        input?.serviceVersion.orEmpty())
}