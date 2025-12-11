package com.example.premierpas.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "extinct_animal")
data class ExtinctAnimalEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "binomial_name")
    val binomialName: String,

    @ColumnInfo(name = "common_name")
    val commonName: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "wiki_link")
    val wikiLink: String,

    @ColumnInfo(name = "last_record")
    val lastRecord: String,

    @ColumnInfo(name = "image_src")
    val imageSrc: String,

    @ColumnInfo(name = "short_desc")
    val shortDesc: String,

    @ColumnInfo(name = "insert_timestamp")
    val insertTimestamp: Long
)
