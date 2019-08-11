package jdr.tv.details.di

import jdr.tv.data.di.dataComponent
import jdr.tv.details.ui.DetailsFragment

fun DetailsFragment.inject() {
    DaggerDetailsComponent.builder()
        .fragment(this)
        .dataComponent(dataComponent())
        .build()
        .inject(this)
}
