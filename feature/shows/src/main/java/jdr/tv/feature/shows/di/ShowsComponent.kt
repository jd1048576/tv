package jdr.tv.feature.shows.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.data.di.DataComponent
import jdr.tv.data.di.FragmentScope
import jdr.tv.data.di.Injector
import jdr.tv.feature.shows.ui.ShowsFragment

@FragmentScope
@Component(modules = [ShowsModule::class], dependencies = [DataComponent::class])
interface ShowsComponent : Injector<ShowsFragment> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: ShowsFragment, dataComponent: DataComponent): ShowsComponent
    }
}
