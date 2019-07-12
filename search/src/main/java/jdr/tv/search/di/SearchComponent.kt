package jdr.tv.search.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.base.di.BaseComponent
import jdr.tv.base.di.FragmentScope
import jdr.tv.data.di.DataComponent
import jdr.tv.search.ui.SearchFragment

@FragmentScope
@Component(modules = [SearchModule::class], dependencies = [DataComponent::class])
interface SearchComponent : BaseComponent<SearchFragment> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun fragment(fragment: SearchFragment): Builder

        fun dataComponent(dataComponent: DataComponent): Builder
        fun build(): SearchComponent
    }
}
