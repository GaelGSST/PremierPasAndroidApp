package com.example.premierpas.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.premierpas.data.local.model.ExtinctAnimalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtinctAnimalDao {
    @Query("SELECT * FROM extinct_animal ORDER BY insert_timestamp DESC")
    fun selectAll(): Flow<List<ExtinctAnimalEntity>>

    @Query("SELECT * FROM extinct_animal WHERE id = :id")
    fun selectById(id: Long): Flow<ExtinctAnimalEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(animal: ExtinctAnimalEntity)

    @Query("DELETE FROM extinct_animal")
    fun deleteAll()

    @Query("DELETE FROM extinct_animal WHERE id = :id")
    fun deleteById(id: Long)
}
