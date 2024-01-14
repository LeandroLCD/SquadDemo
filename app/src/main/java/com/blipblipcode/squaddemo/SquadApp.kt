package com.blipblipcode.squaddemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SquadApp: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}