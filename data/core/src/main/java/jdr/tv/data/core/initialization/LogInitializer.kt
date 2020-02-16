package jdr.tv.data.core.initialization

import com.google.firebase.crashlytics.FirebaseCrashlytics
import jdr.tv.common.log.Log
import jdr.tv.data.core.BuildConfig
import javax.inject.Inject

class LogInitializer @Inject constructor() : Initializer {
    override fun initialize() {
        Log.addLogger { level, message -> FirebaseCrashlytics.getInstance().log("${level.symbol}: $message") }
        if (BuildConfig.DEBUG) Log.addLogger { level, message -> android.util.Log.println(level.value, "jdr.tv", message) }
    }
}
