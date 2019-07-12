package jdr.tv.details.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.base.di.BaseComponent
import jdr.tv.base.di.FragmentScope
import jdr.tv.data.di.DataComponent
import jdr.tv.details.ui.DetailsFragment

@FragmentScope
@Component(modules = [DetailsModule::class], dependencies = [DataComponent::class])
interface DetailsComponent : BaseComponent<DetailsFragment> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun fragment(fragment: DetailsFragment): Builder

        fun dataComponent(dataComponent: DataComponent): Builder
        fun build(): DetailsComponent
    }
}
