package jdr.tv.data.mapper

import jdr.tv.data.local.entities.ProductionCompany
import jdr.tv.data.remote.entities.RemoteProductionCompany

fun RemoteProductionCompany.toProductionCompany(): ProductionCompany {
    return ProductionCompany(id, logoPath, name, originCountry)
}
