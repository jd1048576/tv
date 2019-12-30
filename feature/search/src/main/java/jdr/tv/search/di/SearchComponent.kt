package jdr.tv.search.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.base.di.FragmentScope
import jdr.tv.base.di.Injector
import jdr.tv.data.di.DataComponent
import jdr.tv.search.ui.SearchFragment

@FragmentScope
@Component(modules = [SearchModule::class], dependencies = [DataComponent::class])
interface SearchComponent : Injector<SearchFragment> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: SearchFragment, dataComponent: DataComponent): SearchComponent
    }
}
