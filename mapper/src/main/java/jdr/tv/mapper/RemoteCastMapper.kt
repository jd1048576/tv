package jdr.tv.mapper

import jdr.tv.local.entities.Cast
import jdr.tv.remote.entities.RemoteCast

fun RemoteCast.toCast(): Cast {
    return Cast(character, id, name, gender, profilePath)
}
