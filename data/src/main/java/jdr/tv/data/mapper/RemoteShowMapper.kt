package jdr.tv.data.mapper

import jdr.tv.local.entities.Show
import jdr.tv.remote.entities.RemoteShow
import java.time.Instant

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
