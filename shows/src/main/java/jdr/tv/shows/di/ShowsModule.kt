package jdr.tv.shows.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.shows.ui.ShowsFragment
import jdr.tv.shows.ui.ShowsRepository
import jdr.tv.shows.ui.ShowsViewModel
import jdr.tv.ui.extensions.appCompatActivity
import jdr.tv.viewmodel.ViewModelProviderFactory

@Module
object ShowsModule {

    @Provides
    @JvmStatic
    fun provideViewModelProviderFactory(repository: ShowsRepository): ViewModelProvider.Factory {
        return ViewModelProviderFactory { ShowsViewModel(repository) }
    }

    @Provides
    @JvmStatic
    fun provideViewModel(fragment: ShowsFragment, factory: ViewModelProvider.Factory): ShowsViewModel {
        return ViewModelProvider(fragment.appCompatActivity, factory).get(ShowsViewModel::class.java)
    }
}
