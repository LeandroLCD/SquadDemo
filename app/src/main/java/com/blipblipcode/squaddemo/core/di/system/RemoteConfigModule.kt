package com.blipblipcode.squaddemo.core.di.system

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RemoteConfigModule {

    @Provides
    fun provideRemoteConfig(): FirebaseRemoteConfig = Firebase.remoteConfig
}


