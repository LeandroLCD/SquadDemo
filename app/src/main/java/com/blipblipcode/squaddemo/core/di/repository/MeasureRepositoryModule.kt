package com.blipblipcode.squaddemo.core.di.repository

import com.blipblipcode.squaddemo.data.MeasureRepository
import com.blipblipcode.squaddemo.domain.IMeasureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MeasureRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindMeasureRepository(repository: MeasureRepository): IMeasureRepository


}