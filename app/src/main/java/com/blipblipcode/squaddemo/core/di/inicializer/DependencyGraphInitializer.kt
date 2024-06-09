package com.blipblipcode.squaddemo.core.di.inicializer

import android.content.Context
import androidx.startup.Initializer
import com.blipblipcode.squaddemo.core.di.InitializerEntryPoint

class DependencyGraphInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}