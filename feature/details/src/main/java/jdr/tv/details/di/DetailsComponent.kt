package jdr.tv.details.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.base.di.FragmentScope
import jdr.tv.base.di.Injector
import jdr.tv.data.di.DataComponent
import jdr.tv.details.ui.DetailsFragment

@FragmentScope
@Component(modules = [DetailsModule::class], dependencies = [DataComponent::class])
interface DetailsComponent : Injector<DetailsFragment> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: DetailsFragment, dataComponent: DataComponent): DetailsComponent
    }
}
