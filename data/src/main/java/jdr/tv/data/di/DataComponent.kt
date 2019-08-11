package jdr.tv.data.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import jdr.tv.local.Database
import jdr.tv.local.di.LocalModule
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.di.RemoteModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, LocalModule::class, RemoteModule::class])
interface DataComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): DataComponent
    }

    fun client(): OkHttpClient
    fun database(): Database
    fun tmdbApi(): TmdbApi
}

abstract class DataComponentApplication : Application() {

    protected abstract val dataComponent: DataComponent

    companion object {
        fun dataComponent(context: Context): DataComponent = (context.applicationContext as DataComponentApplication).dataComponent
    }
}

fun Context.dataComponent(): DataComponent = DataComponentApplication.dataComponent(this)
fun Fragment.dataComponent(): DataComponent = context!!.dataComponent()
