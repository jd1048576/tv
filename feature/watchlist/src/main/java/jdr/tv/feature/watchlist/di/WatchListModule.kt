package jdr.tv.feature.watchlist.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.watchlist.ui.WatchListViewModel
import javax.inject.Provider

@Module
object WatchListModule {

    @Provides
    fun providesViewModelProviderFactory(provider: Provider<WatchListViewModel>): ViewModelProvider.Factory {
        return ViewModelProviderFactory(provider::get)
    }
}
