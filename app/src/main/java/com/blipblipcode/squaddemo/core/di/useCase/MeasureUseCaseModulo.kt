package com.blipblipcode.squaddemo.core.di.useCase

import com.blipblipcode.squaddemo.domain.useCase.AddMeasureUseCase
import com.blipblipcode.squaddemo.domain.useCase.DeleteMeasureUseCase
import com.blipblipcode.squaddemo.domain.useCase.GetMeasureListUseCase
import com.blipblipcode.squaddemo.domain.useCase.UpdateMeasureUseCase
import com.blipblipcode.squaddemo.ui.home.interfaces.IAddMeasureUseCase
import com.blipblipcode.squaddemo.ui.home.interfaces.IDeleteMeasureUseCase
import com.blipblipcode.squaddemo.ui.home.interfaces.IGetMeasureListUseCase
import com.blipblipcode.squaddemo.ui.home.interfaces.IUpdateMeasureUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MeasureUseCaseModulo {
    @Singleton
    @Binds
    abstract fun bindsAddMeasure(addMeasureUseCase: AddMeasureUseCase): IAddMeasureUseCase

    @Singleton
    @Binds
    abstract fun bindsGetMeasure(getMeasureListUseCase: GetMeasureListUseCase): IGetMeasureListUseCase

    @Singleton
    @Binds
    abstract fun bindsUpdateMeasure(updateMeasureUseCase: UpdateMeasureUseCase): IUpdateMeasureUseCase

    @Singleton
    @Binds
    abstract fun bindsDeleteMeasure(deleteMeasureUseCase: DeleteMeasureUseCase): IDeleteMeasureUseCase


}