package jdr.tv.data.mapper

import jdr.tv.data.local.entities.Crew
import jdr.tv.data.remote.entities.RemoteCrew

fun RemoteCrew.toCrew(): Crew {
    return Crew(department, id, name, gender, job, profilePath)
}
