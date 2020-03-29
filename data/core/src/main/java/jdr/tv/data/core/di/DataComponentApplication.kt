package jdr.tv.data.core.di

import android.app.Application
import android.content.Context
import javax.inject.Inject
import jdr.tv.data.core.initialization.AppInitializer

open class DataComponentApplication : Application() {

    private lateinit var dataComponent: DataComponent

    @Inject
    lateinit var initializer: AppInitializer

    override fun onCreate() {
        super.onCreate()
        dataComponent = DaggerDataComponent.factory().create(this)
        dataComponent.inject(this)
        initializer.initialize()
    }

    companion object {
        fun dataComponent(context: Context): DataComponent = (context.applicationContext as DataComponentApplication).dataComponent
    }
}

fun Context.dataComponent(): DataComponent = DataComponentApplication.dataComponent(this)
