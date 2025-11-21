package com.example.premierpas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.premierpas.data.local.dao.AndroidVersionDao
import com.example.premierpas.data.local.model.AndroidVersionEntity

@Database(
    entities = [
        AndroidVersionEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class CustomRoomDatabase : RoomDatabase() {
    abstract fun androidVersionDao(): AndroidVersionDao
}

