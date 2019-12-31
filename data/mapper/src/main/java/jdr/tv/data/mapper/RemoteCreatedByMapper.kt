package jdr.tv.data.mapper

import jdr.tv.data.local.entities.CreatedBy
import jdr.tv.data.remote.entities.RemoteCreatedBy

fun RemoteCreatedBy.toCreatedBy(): CreatedBy {
    return CreatedBy(id, name, gender, profilePath)
}
