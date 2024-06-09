package com.blipblipcode.squaddemo.core.di.inicializer

import android.content.Context
import androidx.startup.Initializer
import com.blipblipcode.squaddemo.core.di.InitializerEntryPoint
import com.blipblipcode.squaddemo.data.MeasureRepository
import javax.inject.Inject

class MeasureRepositoryInitializer: Initializer<MeasureRepository> {

    @Inject
    lateinit var repository: MeasureRepository
    override fun create(context: Context): MeasureRepository {
        InitializerEntryPoint.resolve(context).inject(this)
        return repository
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(DependencyGraphInitializer::class.java)
    }
}