package jdr.tv.details.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.details.ui.DetailsFragment
import jdr.tv.details.ui.DetailsRepository
import jdr.tv.details.ui.DetailsViewModel
import jdr.tv.viewmodel.ViewModelProviderFactory

@Module
object DetailsModule {

    @Provides
    @JvmStatic
    fun provideViewModelProviderFactory(repository: DetailsRepository): ViewModelProvider.Factory {
        return ViewModelProviderFactory { DetailsViewModel(repository) }
    }

    @Provides
    @JvmStatic
    fun provideViewModel(fragment: DetailsFragment, factory: ViewModelProvider.Factory): DetailsViewModel {
        return ViewModelProvider(fragment, factory).get(DetailsViewModel::class.java)
    }
}
