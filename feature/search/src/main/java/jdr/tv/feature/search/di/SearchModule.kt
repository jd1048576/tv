package jdr.tv.feature.search.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.search.ui.SearchViewModel
import javax.inject.Provider

@Module
object SearchModule {

    @Provides
    fun providesViewModelProviderFactory(provider: Provider<SearchViewModel>): ViewModelProvider.Factory {
        return ViewModelProviderFactory(provider::get)
    }
}
