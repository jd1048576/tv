package jdr.tv.mapper

import java.time.Instant
import jdr.tv.local.entities.Show
import jdr.tv.remote.entities.RemoteShow

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
