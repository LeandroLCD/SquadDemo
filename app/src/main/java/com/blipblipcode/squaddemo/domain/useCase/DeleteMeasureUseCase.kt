package com.blipblipcode.squaddemo.domain.useCase

import com.blipblipcode.squaddemo.domain.IMeasureRepository
import com.blipblipcode.squaddemo.ui.home.interfaces.IDeleteMeasureUseCase
import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import javax.inject.Inject


class DeleteMeasureUseCase @Inject constructor(private val repository: IMeasureRepository) :
    IDeleteMeasureUseCase {
    override suspend operator fun invoke(item: MeasureModel) {
        repository.deleteMeasure(item)
    }
}