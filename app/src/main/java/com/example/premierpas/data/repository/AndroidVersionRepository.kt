package com.example.premierpas.data.repository
import com.example.premierpas.architecture.CustomApplication
import com.example.premierpas.data.local.model.AndroidVersionEntity
import com.example.premierpas.data.mapping.toDataObject
import com.example.premierpas.data.mapping.toRoomObject
import com.example.premierpas.data.model.ItemModelData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidVersionRepository {
    private val androidVersionDao = CustomApplication.instance.applicationDatabase.androidVersionDao()

    fun selectAndroidVersionById(id: Long): Flow<AndroidVersionEntity?> {
        return androidVersionDao.selectById(id)
    }

    fun selectAllAndroidVersion(): Flow<List<ItemModelData>> {
        return androidVersionDao.selectAll().map { androidVersionEntity: List<AndroidVersionEntity> ->
            androidVersionEntity.toDataObject()
        }
    }

    fun insertAndroidVersion(myAndroidModelData: ItemModelData) {
        androidVersionDao.insert(myAndroidModelData.toRoomObject())
    }

    fun deleteAllAndroidVersion() {
        androidVersionDao.deleteAll()
    }

}