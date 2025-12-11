package com.example.premierpas.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AnimalApiResponse(
    val status: String,
    val data: List<AnimalDto>
)

@Serializable
data class AnimalDto(
    val binomialName: String,
    val commonName: String,
    val location: String,
    val wikiLink: String,
    val lastRecord: String,
    val imageSrc: String,
    val shortDesc: String
)
