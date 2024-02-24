package com.blipblipcode.squaddemo.ui.home.interfaces

import com.blipblipcode.squaddemo.ui.home.models.MeasureModel

interface IDeleteMeasureUseCase {
    suspend fun invoke(item: MeasureModel)
}