package jdr.tv.data.mapper

import jdr.tv.local.entities.Network
import jdr.tv.remote.entities.RemoteNetwork

fun RemoteNetwork.toNetwork(): Network {
    return Network(id, logoPath, name, originCountry)
}
