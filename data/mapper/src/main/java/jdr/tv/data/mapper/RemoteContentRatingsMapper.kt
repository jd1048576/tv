package jdr.tv.data.mapper

import jdr.tv.data.local.entities.ContentRating
import jdr.tv.data.remote.entities.RemoteContentRating

fun RemoteContentRating.toContentRating(): ContentRating {
    return ContentRating(iso31661, rating)
}
