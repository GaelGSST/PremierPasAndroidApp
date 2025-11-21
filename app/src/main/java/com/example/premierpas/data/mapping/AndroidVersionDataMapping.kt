package com.example.premierpas.data.mapping

import com.example.premierpas.data.local.model.AndroidVersionEntity
import com.example.premierpas.data.model.ItemModelData

fun ItemModelData.toRoomObject(): AndroidVersionEntity {
    return AndroidVersionEntity(
        id = id,
        name = versionName,
        code = versionNumber,
        isActive = isActive,
        year = year,
        imageUrl = imageUrl
    )
}

fun List<AndroidVersionEntity>.toDataObject(): List<ItemModelData> {
    return this.map { eachItem ->
        ItemModelData(
            id = eachItem.id,
            versionName = eachItem.name,
            versionNumber = eachItem.code,
            isActive = eachItem.isActive,
            year = eachItem.year,
            imageUrl = eachItem.imageUrl
        )
    }
}
