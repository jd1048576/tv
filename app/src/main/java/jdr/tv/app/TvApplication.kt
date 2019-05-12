package jdr.tv.app

import android.app.Application
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

class TvApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setDefaultNightMode(PreferenceManager.getDefaultSharedPreferences(this).getString("THEME", "-1")!!.toInt())
    }
}