package jdr.tv.shows.di

import jdr.tv.data.di.dataComponent
import jdr.tv.shows.ui.ShowsFragment

fun ShowsFragment.inject() {
    DaggerShowsComponent.builder()
        .fragment(this)
        .dataComponent(dataComponent())
        .build()
        .inject(this)
}
