package com.example.spaceinformer.model.NasaAPI.get

data class Item(
    val `data`: List<Data>,
    val href: String,
    val links: List<Link>
)