package jdr.tv.data.mapper

import jdr.tv.local.entities.CreatedBy
import jdr.tv.remote.entities.RemoteCreatedBy

fun RemoteCreatedBy.toCreatedBy(): CreatedBy {
    return CreatedBy(id, name, gender, profilePath)
}
