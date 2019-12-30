package jdr.tv.feature.details.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.navigation.GlobalActions
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.details.ui.DetailsFragment
import jdr.tv.feature.details.ui.DetailsRepository
import jdr.tv.feature.details.ui.DetailsViewModel

@Module
object DetailsModule {

    @Provides
    fun providesShowId(fragment: DetailsFragment): Long {
        return GlobalActions.ActionDetails.fromBundle(fragment.arguments).showId
    }

    @Provides
    fun providesViewModelProviderFactory(showId: Long, repository: DetailsRepository): ViewModelProvider.Factory {
        return ViewModelProviderFactory { DetailsViewModel(showId, repository) }
    }

    @Provides
    fun providesViewModel(fragment: DetailsFragment, factory: ViewModelProvider.Factory): DetailsViewModel {
        return ViewModelProvider(fragment, factory).get(DetailsViewModel::class.java)
    }
}
