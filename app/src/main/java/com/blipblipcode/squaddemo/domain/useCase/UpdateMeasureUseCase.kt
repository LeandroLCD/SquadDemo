package com.blipblipcode.squaddemo.domain.useCase

import com.blipblipcode.squaddemo.domain.IMeasureRepository
import com.blipblipcode.squaddemo.ui.home.interfaces.IUpdateMeasureUseCase
import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import javax.inject.Inject

class UpdateMeasureUseCase @Inject constructor(private val repository: IMeasureRepository) :
    IUpdateMeasureUseCase {
    override suspend operator fun invoke(item: MeasureModel) {
        repository.updateMeasure(item)
    }
}