package jdr.tv.app

import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import jdr.tv.base.Log
import jdr.tv.data.di.DaggerDataComponent
import jdr.tv.data.di.DataComponent
import jdr.tv.data.di.DataComponentApplication

class App : DataComponentApplication() {

    override lateinit var dataComponent: DataComponent

    override fun onCreate() {
        super.onCreate()

        Log.addLogger(CrashlyticsLogger())

        AppCompatDelegate.setDefaultNightMode(PreferenceManager.getDefaultSharedPreferences(this).getString("THEME", "-1")!!.toInt())
        dataComponent = DaggerDataComponent.builder().context(this).build()
    }
}
