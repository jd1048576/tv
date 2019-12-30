package jdr.tv.search.di

import jdr.tv.data.di.DataComponent
import jdr.tv.search.ui.SearchFragment

fun SearchFragment.inject(component: DataComponent) {
    DaggerSearchComponent.factory()
        .create(this, component)
        .inject(this)
}
