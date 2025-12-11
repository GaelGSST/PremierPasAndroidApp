package com.example.premierpas.data.repository

import com.example.premierpas.architecture.CustomApplication
import com.example.premierpas.data.local.model.ExtinctAnimalEntity
import com.example.premierpas.data.mapping.toAnimalModelData
import com.example.premierpas.data.mapping.toDataObject
import com.example.premierpas.data.mapping.toRoomObject
import com.example.premierpas.data.model.AnimalModelData
import com.example.premierpas.data.remote.AnimalApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimalRepository {
    private val extinctAnimalDao = CustomApplication.instance.applicationDatabase.extinctAnimalDao()
    private val apiService = AnimalApiService()

    fun selectAnimalById(id: Long): Flow<ExtinctAnimalEntity?> {
        return extinctAnimalDao.selectById(id)
    }

    fun selectAllAnimals(): Flow<List<AnimalModelData>> {
        return extinctAnimalDao.selectAll().map { entities: List<ExtinctAnimalEntity> ->
            entities.toDataObject()
        }
    }

    suspend fun fetchAndInsertRandomAnimal() {
        try {
            val response = apiService.fetchAnimals()
            if (response.status == "success" && response.data.isNotEmpty()) {
                // Pick a random animal from the response
                val randomAnimal = response.data.random()
                val currentTimestamp = System.currentTimeMillis()
                val animalModelData = randomAnimal.toAnimalModelData(currentTimestamp)
                extinctAnimalDao.insert(animalModelData.toRoomObject())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle error - could throw or log
        }
    }

    fun deleteAllAnimals() {
        extinctAnimalDao.deleteAll()
    }

    fun closeApiService() {
        apiService.close()
    }
}
