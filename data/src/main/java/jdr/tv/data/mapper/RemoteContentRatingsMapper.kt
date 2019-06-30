package jdr.tv.data.mapper

import jdr.tv.local.entities.ContentRating
import jdr.tv.remote.entities.RemoteContentRating

fun RemoteContentRating.toContentRating(): ContentRating {
    return ContentRating(iso31661, rating)
}