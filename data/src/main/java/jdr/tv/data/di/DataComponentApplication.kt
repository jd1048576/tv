package jdr.tv.data.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment

abstract class DataComponentApplication : Application() {

    private lateinit var dataComponent: DataComponent

    protected abstract fun createDataComponent(): DataComponent

    override fun onCreate() {
        super.onCreate()
        dataComponent = createDataComponent()
        dataComponent.appInitializer().initialize()
    }

    companion object {
        fun dataComponent(context: Context): DataComponent = (context.applicationContext as DataComponentApplication).dataComponent
    }
}

fun Context.dataComponent(): DataComponent = DataComponentApplication.dataComponent(this)
fun Fragment.dataComponent(): DataComponent = context!!.dataComponent()
