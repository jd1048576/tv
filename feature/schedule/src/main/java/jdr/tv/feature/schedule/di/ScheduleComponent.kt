package jdr.tv.feature.schedule.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.data.core.di.DataComponent
import jdr.tv.data.core.di.FragmentScope
import jdr.tv.data.core.di.Injector
import jdr.tv.feature.schedule.ui.ScheduleFragment

@FragmentScope
@Component(modules = [ScheduleModule::class], dependencies = [DataComponent::class])
interface ScheduleComponent : Injector<ScheduleFragment> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: ScheduleFragment, dataComponent: DataComponent): ScheduleComponent
    }
}
