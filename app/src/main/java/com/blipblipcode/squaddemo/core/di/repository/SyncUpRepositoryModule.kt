package com.blipblipcode.squaddemo.core.di.repository

import android.content.Context
import com.blipblipcode.squaddemo.data.SyncUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SyncUpRepositoryModule {
    @Provides
    @Singleton
    fun provideSyncUpRepository(@ApplicationContext context: Context) = SyncUpRepository(context)
}