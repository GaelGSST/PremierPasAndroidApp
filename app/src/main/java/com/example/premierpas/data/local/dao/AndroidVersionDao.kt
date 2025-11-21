package com.example.premierpas.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.premierpas.data.local.model.AndroidVersionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AndroidVersionDao {
    @Query("SELECT * FROM android_version ORDER BY name ASC")
    fun selectAll(): Flow<List<AndroidVersionEntity>>

    @Query("SELECT * FROM android_version WHERE id = :id")
    fun selectById(id: Long): Flow<AndroidVersionEntity?>

    @Query("SELECT * FROM android_version WHERE isActive = true ORDER BY name ASC")
    fun selectActive(): Flow<List<AndroidVersionEntity>>

    @Query("SELECT * FROM android_version WHERE year = :year ORDER BY name ASC")
    fun selectByYear(year: Int): Flow<List<AndroidVersionEntity>>

    @Query("SELECT * FROM android_version WHERE year = :year ORDER BY name ASC")
    fun selectActiveByYear(year: Int): Flow<List<AndroidVersionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(androidVersion: AndroidVersionEntity)

    @Query("DELETE FROM android_version")
    fun deleteAll()

    @Query("DELETE FROM android_version WHERE id = :id")
    fun deleteById(id: Long)
}
