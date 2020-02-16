package jdr.tv.feature.shows.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.shows.ui.ShowsViewModel
import javax.inject.Provider

@Module
object ShowsModule {

    @Provides
    fun providesViewModelProviderFactory(provider: Provider<ShowsViewModel>): ViewModelProvider.Factory {
        return ViewModelProviderFactory(provider::get)
    }
}
