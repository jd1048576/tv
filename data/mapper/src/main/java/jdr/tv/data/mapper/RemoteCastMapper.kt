package jdr.tv.data.mapper

import jdr.tv.data.local.entities.Cast
import jdr.tv.data.remote.entities.RemoteCast

fun RemoteCast.toCast(): Cast {
    return Cast(character, id, name, gender, profilePath)
}
