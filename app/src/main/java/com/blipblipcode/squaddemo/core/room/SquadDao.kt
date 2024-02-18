package com.blipblipcode.squaddemo.core.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.blipblipcode.squaddemo.core.room.entity.MeasureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SquadDao {

    @Query("SELECT * FROM MeasureEntity")
    fun getAllMeasure(): Flow<List<MeasureEntity>>

    @Insert
    suspend fun addMeasure(item: MeasureEntity)

    @Update
    suspend fun updateMeasure(item: MeasureEntity)

    @Delete
    suspend fun deleteMeasure(item: MeasureEntity)

}