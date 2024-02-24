package com.blipblipcode.squaddemo.ui.home.interfaces

import com.blipblipcode.squaddemo.ui.home.models.MeasureModel

interface IUpdateMeasureUseCase {
    suspend operator fun invoke(item: MeasureModel)
}