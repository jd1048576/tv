package jdr.tv.feature.search.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.data.di.DataComponent
import jdr.tv.data.di.FragmentScope
import jdr.tv.data.di.Injector
import jdr.tv.feature.search.ui.SearchFragment

@FragmentScope
@Component(modules = [SearchModule::class], dependencies = [DataComponent::class])
interface SearchComponent : Injector<SearchFragment> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: SearchFragment, dataComponent: DataComponent): SearchComponent
    }
}
