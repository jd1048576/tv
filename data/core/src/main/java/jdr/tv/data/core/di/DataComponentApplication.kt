package jdr.tv.data.core.di

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import javax.inject.Inject
import jdr.tv.data.core.initialization.AppInitializer

open class DataComponentApplication : Application(), Configuration.Provider {

    private lateinit var dataComponent: DataComponent

    @Inject
    lateinit var initializer: AppInitializer

    @Inject
    lateinit var workConfiguration: Configuration

    override fun onCreate() {
        super.onCreate()
        dataComponent = DaggerDataComponent.factory().create(this)
        dataComponent.inject(this)
        initializer.initialize()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return workConfiguration
    }

    companion object {
        fun dataComponent(context: Context): DataComponent = (context.applicationContext as DataComponentApplication).dataComponent
    }
}

fun Context.dataComponent(): DataComponent = DataComponentApplication.dataComponent(this)
