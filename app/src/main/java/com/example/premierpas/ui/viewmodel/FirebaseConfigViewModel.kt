package com.example.premierpas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierpas.data.repository.FirebaseRepository
import com.example.premierpas.ui.model.FirebaseConfigUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FirebaseConfigViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository()

    private val _configState = MutableStateFlow(
        FirebaseConfigUi(
            isDarkModeEnabled = false,
            themeColor = "blue"
        )
    )
    val configState: StateFlow<FirebaseConfigUi> = _configState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadConfig()
    }

    private fun loadConfig() {
        val config = firebaseRepository.getFirebaseConfig()
        _configState.value = FirebaseConfigUi(
            isDarkModeEnabled = config.isDarkModeEnabled,
            themeColor = config.themeColor
        )
    }

    fun fetchAndRefreshConfig() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = firebaseRepository.fetchAndActivateConfig()
                if (success) {
                    loadConfig()
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
