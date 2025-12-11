package com.example.premierpas.architecture

import android.app.Application
import androidx.room.Room
import com.example.premierpas.data.local.CustomRoomDatabase
import firebase.RemoteConfigManager

class CustomApplication : Application() {
    companion object {
        lateinit var instance: CustomApplication
    }

    val applicationDatabase: CustomRoomDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            CustomRoomDatabase::class.java,
            "PremierPasDatabase"
        ).fallbackToDestructiveMigration(false)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        RemoteConfigManager.init()
    }
}
