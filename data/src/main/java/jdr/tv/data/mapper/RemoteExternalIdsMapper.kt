package jdr.tv.data.mapper

import jdr.tv.local.entities.ExternalIds
import jdr.tv.remote.entities.RemoteExternalIds

fun RemoteExternalIds.toExtenalIds(): ExternalIds {
    return ExternalIds(imdbId, facebookId, instagramId, twitterId)
}