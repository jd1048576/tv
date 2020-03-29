package jdr.tv.feature.schedule.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import jdr.tv.common.viewmodel.ViewModelProviderFactory
import jdr.tv.feature.schedule.ui.ScheduleViewModel
import javax.inject.Provider

@Module
object ScheduleModule {

    @Provides
    fun providesViewModelProviderFactory(provider: Provider<ScheduleViewModel>): ViewModelProvider.Factory {
        return ViewModelProviderFactory(provider::get)
    }
}
