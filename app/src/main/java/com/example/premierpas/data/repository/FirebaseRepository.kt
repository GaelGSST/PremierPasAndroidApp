package com.example.premierpas.data.repository

import com.example.premierpas.data.model.FirebaseConfigData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    suspend fun fetchAndActivateConfig(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getFirebaseConfig(): FirebaseConfigData {
        return FirebaseConfigData(
            isDarkModeEnabled = remoteConfig.getBoolean("dark_mode_enabled"),
            themeColor = remoteConfig.getString("theme_color")
        )
    }
}
