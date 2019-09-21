package jdr.tv.data.initialization

import javax.inject.Inject

class AppInitializer @Inject constructor(private val initializerSet: Set<@JvmSuppressWildcards Initializer>) {

    fun initialize() {
        initializerSet.forEach { it.initialize() }
    }
}
