package jdr.tv.search.di

import jdr.tv.data.di.dataComponent
import jdr.tv.search.ui.SearchFragment

fun SearchFragment.inject() {
    DaggerSearchComponent.factory()
        .create(this, dataComponent())
        .inject(this)
}
