package jdr.tv.data.mapper

import jdr.tv.local.entities.Crew
import jdr.tv.remote.entities.RemoteCrew

fun RemoteCrew.toCrew(): Crew {
    return Crew(department, id, name, gender, job, profilePath)
}
