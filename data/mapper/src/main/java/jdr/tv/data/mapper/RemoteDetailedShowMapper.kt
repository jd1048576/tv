package jdr.tv.data.mapper

import java.time.Instant
import jdr.tv.data.local.entities.Details
import jdr.tv.data.local.entities.Show
import jdr.tv.data.remote.entities.RemoteDetailedShow

fun RemoteDetailedShow.toShow(): Show {
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

fun RemoteDetailedShow.toDetails(): Details {
    return Details(
        createdByList.map { it.toCreatedBy() },
        episodeRunTimeList.firstOrNull() ?: 0,
        homepage,
        id,
        inProduction,
        lastAirDate,
        networkList.map { it.toNetwork() },
        productionCompanyList.map { it.toProductionCompany() },
        status,
        type,
        contentRatings.contentRatingList.map { it.toContentRating() },
        credits.castList.map { it.toCast() },
        credits.crewList.map { it.toCrew() },
        externalIds.toExternalIds(),
        videos.videoList.map { it.toVideo() },
        Instant.now()
    )
}
