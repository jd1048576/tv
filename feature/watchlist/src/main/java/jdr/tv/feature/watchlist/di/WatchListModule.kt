package jdr.tv.feature.watchlist.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.watchlist.ui.WatchListFragment
import jdr.tv.feature.watchlist.ui.WatchListRepository
import jdr.tv.feature.watchlist.ui.WatchListViewModel

@Module
object WatchListModule {

    @Provides
    fun providesViewModelProviderFactory(repository: WatchListRepository): ViewModelProvider.Factory {
        return ViewModelProviderFactory { WatchListViewModel(repository) }
    }

    @Provides
    fun providesViewModel(fragment: WatchListFragment, factory: ViewModelProvider.Factory): WatchListViewModel {
        return ViewModelProvider(fragment, factory).get(WatchListViewModel::class.java)
    }
}
