package jdr.tv.feature.shows.di

import jdr.tv.data.core.di.DataComponent
import jdr.tv.feature.shows.ui.ShowsFragment

fun ShowsFragment.inject(component: DataComponent) {
    DaggerShowsComponent.factory()
        .create(this, component)
        .inject(this)
}
