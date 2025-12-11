package com.example.premierpas.data.mapping

import com.example.premierpas.data.local.model.ExtinctAnimalEntity
import com.example.premierpas.data.model.AnimalModelData
import com.example.premierpas.data.remote.dto.AnimalDto

fun AnimalDto.toAnimalModelData(insertTimestamp: Long): AnimalModelData {
    return AnimalModelData(
        id = 0, // Will be auto-generated
        binomialName = binomialName,
        commonName = commonName,
        location = location,
        wikiLink = wikiLink,
        lastRecord = lastRecord,
        imageSrc = imageSrc,
        shortDesc = shortDesc,
        insertTimestamp = insertTimestamp
    )
}

fun AnimalModelData.toRoomObject(): ExtinctAnimalEntity {
    return ExtinctAnimalEntity(
        id = id,
        binomialName = binomialName,
        commonName = commonName,
        location = location,
        wikiLink = wikiLink,
        lastRecord = lastRecord,
        imageSrc = imageSrc,
        shortDesc = shortDesc,
        insertTimestamp = insertTimestamp
    )
}

fun List<ExtinctAnimalEntity>.toDataObject(): List<AnimalModelData> {
    return this.map { entity ->
        AnimalModelData(
            id = entity.id,
            binomialName = entity.binomialName,
            commonName = entity.commonName,
            location = entity.location,
            wikiLink = entity.wikiLink,
            lastRecord = entity.lastRecord,
            imageSrc = entity.imageSrc,
            shortDesc = entity.shortDesc,
            insertTimestamp = entity.insertTimestamp
        )
    }
}
