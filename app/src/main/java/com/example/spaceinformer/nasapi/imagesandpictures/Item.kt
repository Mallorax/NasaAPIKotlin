package com.example.spaceinformer.nasapi.imagesandpictures

data class Item(
    val `data`: List<Data>,
    val href: String,
    val links: List<Link>
)