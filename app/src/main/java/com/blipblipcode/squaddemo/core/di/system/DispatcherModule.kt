package com.blipblipcode.squaddemo.core.di.system

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    fun dispatcherIoProvider(): CoroutineDispatcher = Dispatchers.IO
}