package jdr.tv.data.initialization

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

class ThemeInitializer @Inject constructor(private val sharedPreferences: SharedPreferences) : Initializer {
    override fun initialize() {
        AppCompatDelegate.setDefaultNightMode(sharedPreferences.getString("THEME", "-1")!!.toInt())
    }
}
