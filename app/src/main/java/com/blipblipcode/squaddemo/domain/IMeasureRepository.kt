package com.blipblipcode.squaddemo.domain

import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import kotlinx.coroutines.flow.Flow

interface IMeasureRepository {
    fun getAllMeasure(): Flow<List<MeasureModel>>

    suspend fun addMeasure(item: MeasureModel)

    suspend fun updateMeasure(item: MeasureModel)

    suspend fun deleteMeasure(item: MeasureModel)
}