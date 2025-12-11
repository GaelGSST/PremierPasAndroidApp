package com.example.premierpas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.premierpas.data.local.dao.ExtinctAnimalDao
import com.example.premierpas.data.local.model.ExtinctAnimalEntity

@Database(
    entities = [
        ExtinctAnimalEntity::class
    ],
    version = 3,
    exportSchema = false
)

abstract class CustomRoomDatabase : RoomDatabase() {
    abstract fun extinctAnimalDao(): ExtinctAnimalDao
}

