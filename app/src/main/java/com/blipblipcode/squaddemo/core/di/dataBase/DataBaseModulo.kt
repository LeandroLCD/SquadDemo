package com.blipblipcode.squaddemo.core.di.dataBase

import android.content.Context
import androidx.room.Room
import com.blipblipcode.squaddemo.core.room.SquadDao
import com.blipblipcode.squaddemo.core.room.entity.SquadDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModulo {
    @Provides
    fun provideSquadDao(database: SquadDatabase): SquadDao {
        return database.squadDao()
    }

    @Provides
    @Singleton
    fun provideSquadDatabase(appContext: Context): SquadDatabase {
        return Room.databaseBuilder(appContext, SquadDatabase::class.java, "Database").build()
    }
}