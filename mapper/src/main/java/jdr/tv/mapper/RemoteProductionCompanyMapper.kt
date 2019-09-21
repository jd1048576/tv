package jdr.tv.mapper

import jdr.tv.local.entities.ProductionCompany
import jdr.tv.remote.entities.RemoteProductionCompany

fun RemoteProductionCompany.toProductionCompany(): ProductionCompany {
    return ProductionCompany(id, logoPath, name, originCountry)
}
