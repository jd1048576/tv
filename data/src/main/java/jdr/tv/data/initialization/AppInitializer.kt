package jdr.tv.data.initialization

import android.util.Log
import javax.inject.Inject

class AppInitializer @Inject constructor(private val initializerSet: Set<@JvmSuppressWildcards Initializer>) {

    fun initialize() {
        Log.i("TEST", initializerSet.toString())
        initializerSet.forEach { it.initialize() }
    }
}
