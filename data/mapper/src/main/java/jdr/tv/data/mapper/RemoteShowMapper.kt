package jdr.tv.data.mapper

import java.time.Instant
import jdr.tv.data.local.entities.Show
import jdr.tv.data.remote.entities.RemoteShow

fun RemoteShow.toShow(): Show {
    return Show(
        backdropPath,
        firstAirDate,
        genreList.map { it.name },
        id,
        name,
        originCountryList.firstOrNull(),
        originalLanguage,
        originalName,
        overview,
        popularity,
        posterPath,
        voteAverage,
        voteCount,
        Instant.now()
    )
}
