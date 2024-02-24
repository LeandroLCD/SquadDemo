package com.blipblipcode.squaddemo.ui.home.interfaces

import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import kotlinx.coroutines.flow.Flow

interface IGetMeasureListUseCase {
    operator fun invoke(): Flow<List<MeasureModel>>
}