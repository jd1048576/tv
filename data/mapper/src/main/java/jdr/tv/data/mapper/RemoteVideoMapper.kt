package jdr.tv.data.mapper

import jdr.tv.data.local.entities.Video
import jdr.tv.data.remote.entities.RemoteVideo

fun RemoteVideo.toVideo(): Video {
    return Video(iso31661, key, iso6391, name, site, size, type)
}
