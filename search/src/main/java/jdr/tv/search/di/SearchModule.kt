package jdr.tv.search.di

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import jdr.tv.search.ui.SearchFragment
import jdr.tv.search.ui.SearchViewModel
import jdr.tv.search.ui.SearchViewModelProviderFactory

@Module
object SearchModule {

    @Provides
    @JvmStatic
    fun provideViewModel(fragment: SearchFragment, factory: SearchViewModelProviderFactory): SearchViewModel {
        return ViewModelProviders.of(fragment, factory).get(SearchViewModel::class.java)
    }
}
