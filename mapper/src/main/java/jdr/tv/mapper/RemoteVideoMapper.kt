package jdr.tv.mapper

import jdr.tv.local.entities.Video
import jdr.tv.remote.entities.RemoteVideo

fun RemoteVideo.toVideo(): Video {
    return Video(iso31661, key, iso6391, name, site, size, type)
}
