package com.example.premierpas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierpas.data.model.AnimalModelData
import com.example.premierpas.data.repository.AnimalRepository
import com.example.premierpas.ui.model.AnimalUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AnimalViewModel : ViewModel() {
    private val animalRepository: AnimalRepository by lazy { AnimalRepository() }

    val animalList: StateFlow<List<AnimalUi>> =
        animalRepository.selectAllAnimals()
            .map { animals: List<AnimalModelData> ->
                // Group by hour of insertion
                animals
                    .groupBy { animal ->
                        val date = Date(animal.insertTimestamp)
                        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        formatter.format(date)
                    }
                    .flatMap { (timeGroup, itemsOfGroup) ->
                        buildList {
                            add(AnimalUi.Header(title = "Ajouté le $timeGroup"))
                            addAll(itemsOfGroup.map { animal ->
                                AnimalUi.Item(
                                    id = animal.id,
                                    binomialName = animal.binomialName,
                                    commonName = animal.commonName,
                                    location = animal.location,
                                    wikiLink = animal.wikiLink,
                                    lastRecord = animal.lastRecord,
                                    imageSrc = animal.imageSrc,
                                    shortDesc = animal.shortDesc,
                                    insertTimestamp = animal.insertTimestamp
                                )
                            })
                        }
                    } + listOf(AnimalUi.Footer(totalCount = animals.size))
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.Lazily
            )

    fun fetchAndInsertRandomAnimal() {
        viewModelScope.launch(Dispatchers.IO) {
            animalRepository.fetchAndInsertRandomAnimal()
        }
    }

    fun deleteAllAnimals() {
        viewModelScope.launch(Dispatchers.IO) {
            animalRepository.deleteAllAnimals()
        }
    }

    override fun onCleared() {
        super.onCleared()
        animalRepository.closeApiService()
    }
}
