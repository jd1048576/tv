package jdr.tv.data.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import jdr.tv.data.initialization.CoilInitializer
import jdr.tv.data.initialization.Initializer
import jdr.tv.data.initialization.LogInitializer
import jdr.tv.data.initialization.ThemeInitializer
import jdr.tv.data.initialization.WorkInitializer

@Module
interface InitializationModule {

    @Binds
    @IntoSet
    fun bindCoilInitializer(initializer: CoilInitializer): Initializer

    @Binds
    @IntoSet
    fun bindLogInitializer(initializer: LogInitializer): Initializer

    @Binds
    @IntoSet
    fun bindThemeInitializer(initializer: ThemeInitializer): Initializer

    @Binds
    @IntoSet
    fun bindWorkInitializer(initializer: WorkInitializer): Initializer
}
