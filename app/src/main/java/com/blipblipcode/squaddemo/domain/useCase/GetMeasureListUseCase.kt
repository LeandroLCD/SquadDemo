package com.blipblipcode.squaddemo.domain.useCase

import com.blipblipcode.squaddemo.domain.IMeasureRepository
import com.blipblipcode.squaddemo.ui.home.interfaces.IGetMeasureListUseCase
import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetMeasureListUseCase @Inject constructor(
    private val repository: IMeasureRepository,
    private val dispatcher: CoroutineDispatcher
) : IGetMeasureListUseCase {
    override operator fun invoke(): Flow<List<MeasureModel>> =
        repository.getAllMeasure().flowOn(dispatcher)


}