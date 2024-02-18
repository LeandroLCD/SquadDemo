package com.blipblipcode.squaddemo.core.room.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blipblipcode.squaddemo.core.room.SquadDao

@Database(entities = [MeasureEntity::class], version = 1)
abstract class SquadDatabase : RoomDatabase() {
    abstract fun squadDao(): SquadDao
}