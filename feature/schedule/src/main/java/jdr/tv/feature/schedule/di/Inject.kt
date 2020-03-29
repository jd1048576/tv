package jdr.tv.feature.schedule.di

import jdr.tv.data.core.di.DataComponent
import jdr.tv.feature.schedule.ui.ScheduleFragment

fun ScheduleFragment.inject(component: DataComponent) {
    DaggerScheduleComponent.factory()
        .create(this, component)
        .inject(this)
}
