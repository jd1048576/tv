package jdr.tv.feature.details.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.navigation.GlobalActions
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.details.ui.DetailsFragment
import jdr.tv.feature.details.ui.DetailsViewModel
import javax.inject.Provider

@Module
object DetailsModule {

    @Provides
    fun providesShowId(fragment: DetailsFragment): Long {
        return GlobalActions.ActionDetails.fromBundle(fragment.arguments).showId
    }

    @Provides
    fun providesViewModelProviderFactory(provider: Provider<DetailsViewModel>): ViewModelProvider.Factory {
        return ViewModelProviderFactory(provider::get)
    }
}
