package jdr.tv.shows.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.base.di.BaseComponent
import jdr.tv.base.di.FragmentScope
import jdr.tv.data.di.DataComponent
import jdr.tv.shows.ui.ShowsFragment

@FragmentScope
@Component(modules = [ShowsModule::class], dependencies = [DataComponent::class])
interface ShowsComponent : BaseComponent<ShowsFragment> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: ShowsFragment, dataComponent: DataComponent): ShowsComponent
    }
}
