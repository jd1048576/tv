package jdr.tv.feature.search.di

import jdr.tv.data.core.di.DataComponent
import jdr.tv.feature.search.ui.SearchFragment

fun SearchFragment.inject(component: DataComponent) {
    DaggerSearchComponent.factory()
        .create(this, component)
        .inject(this)
}
