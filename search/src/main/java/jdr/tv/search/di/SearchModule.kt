package jdr.tv.search.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.search.ui.SearchFragment
import jdr.tv.search.ui.SearchRepository
import jdr.tv.search.ui.SearchViewModel
import jdr.tv.viewmodel.ViewModelProviderFactory

@Module
object SearchModule {

    @Provides
    @JvmStatic
    fun provideViewModelProviderFactory(repository: SearchRepository): ViewModelProvider.Factory {
        return ViewModelProviderFactory { SearchViewModel(repository) }
    }

    @Provides
    @JvmStatic
    fun provideViewModel(fragment: SearchFragment, factory: ViewModelProvider.Factory): SearchViewModel {
        return ViewModelProvider(fragment, factory).get(SearchViewModel::class.java)
    }
}
