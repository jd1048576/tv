package jdr.tv.feature.details.di

import dagger.BindsInstance
import dagger.Component
import jdr.tv.data.core.di.DataComponent
import jdr.tv.data.core.di.FragmentScope
import jdr.tv.data.core.di.Injector
import jdr.tv.feature.details.ui.DetailsFragment

@FragmentScope
@Component(modules = [DetailsModule::class], dependencies = [DataComponent::class])
interface DetailsComponent : Injector<DetailsFragment> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: DetailsFragment, dataComponent: DataComponent): DetailsComponent
    }
}
