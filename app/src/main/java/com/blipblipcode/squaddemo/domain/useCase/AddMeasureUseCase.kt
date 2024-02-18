package com.blipblipcode.squaddemo.domain.useCase

import com.blipblipcode.squaddemo.domain.IMeasureRepository
import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import javax.inject.Inject

class AddMeasureUseCase @Inject constructor(private val repository: IMeasureRepository) {
    suspend operator fun invoke(item: MeasureModel) {
        repository.addMeasure(item)
    }
}

//addMeasureUseCase(item)