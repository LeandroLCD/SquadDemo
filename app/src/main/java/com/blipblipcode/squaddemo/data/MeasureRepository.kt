package com.blipblipcode.squaddemo.data

import com.blipblipcode.squaddemo.core.room.SquadDao
import com.blipblipcode.squaddemo.domain.IMeasureRepository
import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MeasureRepository @Inject constructor(private val dao: SquadDao) : IMeasureRepository {


    override fun getAllMeasure(): Flow<List<MeasureModel>> {
        return dao.getAllMeasure().map { list ->
            list.map {
                it.toMeasureModel()
            }
        }
    }

    override suspend fun addMeasure(item: MeasureModel) {
        dao.addMeasure(item.toMeasureEntity())
    }

    override suspend fun updateMeasure(item: MeasureModel) {
        dao.updateMeasure(item.toMeasureEntity())
    }

    override suspend fun deleteMeasure(item: MeasureModel) {
        dao.deleteMeasure(item.toMeasureEntity())
    }

}