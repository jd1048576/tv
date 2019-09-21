package jdr.tv.data.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.work.Configuration
import jdr.tv.data.initialization.AppInitializer
import javax.inject.Inject

abstract class DataComponentApplication : Application(), Configuration.Provider {

    private lateinit var dataComponent: DataComponent

    @Inject
    lateinit var initializer: AppInitializer

    @Inject
    lateinit var workConfiguration: Configuration

    protected abstract fun createDataComponent(): DataComponent

    override fun onCreate() {
        super.onCreate()
        dataComponent = createDataComponent()
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
fun Fragment.dataComponent(): DataComponent = context!!.dataComponent()
