package jdr.tv.feature.search.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.search.ui.SearchFragment
import jdr.tv.feature.search.ui.SearchRepository
import jdr.tv.feature.search.ui.SearchViewModel

@Module
object SearchModule {

    @Provides
    fun providesViewModelProviderFactory(repository: SearchRepository): ViewModelProvider.Factory {
        return ViewModelProviderFactory { SearchViewModel(repository) }
    }

    @Provides
    fun providesViewModel(fragment: SearchFragment, factory: ViewModelProvider.Factory): SearchViewModel {
        return ViewModelProvider(fragment, factory).get(SearchViewModel::class.java)
    }
}
