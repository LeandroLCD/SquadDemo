package com.blipblipcode.squaddemo.ui.home.interfaces

import com.blipblipcode.squaddemo.ui.home.models.MeasureModel

interface IAddMeasureUseCase {
    suspend operator fun invoke(item: MeasureModel)
}