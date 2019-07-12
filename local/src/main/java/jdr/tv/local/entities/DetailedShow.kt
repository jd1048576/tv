package jdr.tv.local.entities

import androidx.room.Embedded

data class DetailedShow(
    @Embedded
    val show: Show,
    @Embedded
    val details: Details
)
