package jdr.tv.app

import com.crashlytics.android.Crashlytics
import jdr.tv.base.Logger

class CrashlyticsLogger : Logger() {
    override fun log(priority: Int, tag: String, message: String) {
        Crashlytics.log(priority, tag, message)
    }
}
