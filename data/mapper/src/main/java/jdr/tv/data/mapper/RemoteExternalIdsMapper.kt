package jdr.tv.data.mapper

import jdr.tv.data.local.entities.ExternalIds
import jdr.tv.data.remote.entities.RemoteExternalIds

fun RemoteExternalIds.toExternalIds(): ExternalIds {
    return ExternalIds(imdbId, facebookId, instagramId, twitterId)
}
