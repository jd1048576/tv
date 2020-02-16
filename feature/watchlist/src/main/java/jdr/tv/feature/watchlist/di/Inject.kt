package jdr.tv.feature.watchlist.di

import jdr.tv.data.core.di.DataComponent
import jdr.tv.feature.watchlist.ui.WatchListFragment

fun WatchListFragment.inject(component: DataComponent) {
    DaggerWatchListComponent.factory()
        .create(this, component)
        .inject(this)
}
