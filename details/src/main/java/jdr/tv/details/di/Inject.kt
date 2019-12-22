package jdr.tv.details.di

import jdr.tv.data.di.DataComponent
import jdr.tv.details.ui.DetailsFragment

fun DetailsFragment.inject(component: DataComponent) {
    DaggerDetailsComponent.factory()
        .create(this, component)
        .inject(this)
}
