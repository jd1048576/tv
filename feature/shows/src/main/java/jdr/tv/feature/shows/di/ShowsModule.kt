package jdr.tv.feature.shows.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.ui.extensions.appCompatActivity
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.shows.ui.ShowsFragment
import jdr.tv.feature.shows.ui.ShowsRepository
import jdr.tv.feature.shows.ui.ShowsViewModel

@Module
object ShowsModule {

    @Provides
    fun providesViewModelProviderFactory(repository: ShowsRepository): ViewModelProvider.Factory {
        return ViewModelProviderFactory { ShowsViewModel(repository) }
    }

    @Provides
    fun providesViewModel(fragment: ShowsFragment, factory: ViewModelProvider.Factory): ShowsViewModel {
        return ViewModelProvider(fragment.appCompatActivity, factory).get(ShowsViewModel::class.java)
    }
}
