package jdr.tv.feature.details.di

import jdr.tv.data.di.DataComponent
import jdr.tv.feature.details.ui.DetailsFragment

fun DetailsFragment.inject(component: DataComponent) {
    DaggerDetailsComponent.factory()
        .create(this, component)
        .inject(this)
}
