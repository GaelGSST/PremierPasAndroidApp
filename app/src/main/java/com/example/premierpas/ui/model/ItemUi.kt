package com.example.premierpas.ui.model

sealed interface ItemUi {
    data class Item(
        val id: Long,
        val versionName: String,
        val versionNumber: String,
        val imageUrl: String,
        val year: Int,
        val isActive: Boolean
    ) : ItemUi

    data class Header(
        val title: String,
    ) : ItemUi

    data class Footer(
        val nbVersion: Int,
    ) : ItemUi
}