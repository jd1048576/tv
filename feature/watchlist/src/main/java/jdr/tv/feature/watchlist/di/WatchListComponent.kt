package jdr.tv.feature.watchlist.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.data.core.di.DataComponent
import jdr.tv.data.core.di.FragmentScope
import jdr.tv.data.core.di.Injector
import jdr.tv.feature.watchlist.ui.WatchListFragment

@FragmentScope
@Component(modules = [WatchListModule::class], dependencies = [DataComponent::class])
interface WatchListComponent : Injector<WatchListFragment> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: WatchListFragment, dataComponent: DataComponent): WatchListComponent
    }
}
