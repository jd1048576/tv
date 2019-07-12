package jdr.tv.search.di

import jdr.tv.app.dataComponent
import jdr.tv.search.ui.SearchFragment

fun SearchFragment.inject() {
    DaggerSearchComponent.builder()
        .fragment(this)
        .dataComponent(dataComponent())
        .build()
        .inject(this)
}
