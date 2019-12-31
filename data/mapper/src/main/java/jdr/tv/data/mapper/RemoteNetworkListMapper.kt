package jdr.tv.data.mapper

import jdr.tv.data.local.entities.Network
import jdr.tv.data.remote.entities.RemoteNetwork

fun RemoteNetwork.toNetwork(): Network {
    return Network(id, logoPath, name, originCountry)
}
