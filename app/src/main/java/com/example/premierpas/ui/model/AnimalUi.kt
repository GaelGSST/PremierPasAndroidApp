package com.example.premierpas.ui.model

sealed interface AnimalUi {
    data class Item(
        val id: Long,
        val binomialName: String,
        val commonName: String,
        val location: String,
        val wikiLink: String,
        val lastRecord: String,
        val imageSrc: String,
        val shortDesc: String,
        val insertTimestamp: Long
    ) : AnimalUi

    data class Header(
        val title: String,
    ) : AnimalUi

    data class Footer(
        val totalCount: Int,
    ) : AnimalUi
}
