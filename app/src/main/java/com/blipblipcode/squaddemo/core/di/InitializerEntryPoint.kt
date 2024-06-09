package com.blipblipcode.squaddemo.core.di

import android.content.Context
import com.blipblipcode.squaddemo.core.di.inicializer.MeasureRepositoryInitializer
import com.blipblipcode.squaddemo.core.di.inicializer.WorkManagerInitializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {
    fun inject(workManagerInitializer: WorkManagerInitializer)
    fun inject(roomInitializer: MeasureRepositoryInitializer)

    companion object {
        fun resolve(context: Context): InitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint::class.java
            )
        }
    }

}