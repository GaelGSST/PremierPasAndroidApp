package com.example.premierpas.data.model

data class AnimalModelData(
    val id: Long,
    val binomialName: String,
    val commonName: String,
    val location: String,
    val wikiLink: String,
    val lastRecord: String,
    val imageSrc: String,
    val shortDesc: String,
    val insertTimestamp: Long
)
