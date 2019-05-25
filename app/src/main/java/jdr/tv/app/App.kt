package jdr.tv.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import jdr.tv.data.di.DaggerDataComponent
import jdr.tv.data.di.DataComponent
import timber.log.Timber

class App : Application() {

    private lateinit var dataComponent: DataComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setDefaultNightMode(PreferenceManager.getDefaultSharedPreferences(this).getString("THEME", "-1")!!.toInt())
        dataComponent = DaggerDataComponent.builder().context(this).build()
    }

    companion object {
        fun dataComponent(context: Context): DataComponent = (context.applicationContext as App).dataComponent
    }
}

fun Activity.dataComponent(): DataComponent = App.dataComponent(this)
fun Fragment.dataComponent(): DataComponent = App.dataComponent(context!!)