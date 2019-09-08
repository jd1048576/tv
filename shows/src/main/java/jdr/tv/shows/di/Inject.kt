package jdr.tv.shows.di

import jdr.tv.data.di.dataComponent
import jdr.tv.shows.ui.ShowsFragment

fun ShowsFragment.inject() {
    DaggerShowsComponent.factory()
        .create(this, dataComponent())
        .inject(this)
}
