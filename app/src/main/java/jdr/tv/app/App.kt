package jdr.tv.app

import jdr.tv.data.di.DaggerDataComponent
import jdr.tv.data.di.DataComponent
import jdr.tv.data.di.DataComponentApplication

class App : DataComponentApplication() {

    override fun createDataComponent(): DataComponent {
        return DaggerDataComponent.factory().create(this)
    }
}
