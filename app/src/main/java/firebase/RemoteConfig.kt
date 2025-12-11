package firebase

import com.example.premierpas.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

object RemoteConfigManager {

    private fun getRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }

    fun init() {
        val remoteConfig = getRemoteConfig()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    fun fetchAndActivate(onComplete: (isSuccess: Boolean) -> Unit) {
        getRemoteConfig().fetchAndActivate().addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }

    fun getTestValue(): Boolean {
        return getRemoteConfig().getBoolean("test")
    }
}


