package com.example.premierpas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierpas.data.model.ItemModelData
import com.example.premierpas.data.repository.AndroidVersionRepository
import com.example.premierpas.ui.model.ItemUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList
import kotlin.random.Random

class AndroidVersionViewModel : ViewModel() {
    // Variable mutable en privée signifie que personne peut modifier le contenu à part le ViewModel
    // lui même. C'est un pattern important à respecter
    private val androidVersionRepository: AndroidVersionRepository by lazy { AndroidVersionRepository() }

    val androidVersionList: StateFlow<List<ItemUi>> =
        androidVersionRepository.selectAllAndroidVersion()
            .map { androidObjectEntities: List<ItemModelData> ->
                androidObjectEntities
                    .groupBy { androidModelData -> androidModelData.versionName }
                    .flatMap { (versionName, itemsOfGroup) ->
                        buildList {
                            add(ItemUi.Header(title = versionName))
                            addAll(itemsOfGroup.map { each ->

                                ItemUi.Item(
                                    id = each.id,
                                    versionName = each.versionName,
                                    versionNumber = each.versionNumber,
                                    imageUrl = each.imageUrl,
                                    isActive = each.isActive,
                                    year = each.year
                                )
                            })
                        }
                    }
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.Lazily
            )

    fun insertAndroidVersion() {
        viewModelScope.launch(Dispatchers.IO) {
            val random = Random.nextInt(0, 10)
            val randomBool = Random.nextInt(0, 2)
            val randomImage = Random.nextInt(100, 1000)
            androidVersionRepository.insertAndroidVersion(
                ItemModelData(
                    id = 0,
                    versionName = "Android $random",
                    versionNumber = "$random",
                    imageUrl = "https://picsum.photos/$randomImage/400",
                    year = 2025,
                    isActive = (randomBool == 1)
                )
            )
        }
    }

    fun deleteAllAndroidVersion() {
        viewModelScope.launch(Dispatchers.IO) {
            androidVersionRepository.deleteAllAndroidVersion()
        }
    }
    fun androidVersionById(id: Long): Flow<ItemUi.Item> {
        return androidVersionRepository
            .selectAndroidVersionById(id)      // Flow<AndroidVersionEntity?>
            .mapNotNull { entity ->
                entity?.let {
                    ItemUi.Item(
                        id = it.id,
                        versionName = it.name,
                        versionNumber = it.code,
                        imageUrl = it.imageUrl,
                        isActive = it.isActive,
                        year = it.year
                    )
                }
            }
    }

}


