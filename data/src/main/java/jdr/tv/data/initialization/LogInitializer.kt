package jdr.tv.data.initialization

import com.crashlytics.android.Crashlytics
import javax.inject.Inject
import jdr.tv.base.Log

class LogInitializer @Inject constructor() : Initializer {
    override fun initialize() {
        Log.addLogger(object : Log() {
            override fun log(priority: Int, tag: String, message: String) {
                Crashlytics.log(priority, tag, message)
            }
        })
    }
}
