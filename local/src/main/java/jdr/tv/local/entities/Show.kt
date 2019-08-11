package jdr.tv.local.entities

import androidx.room.Entity
import androidx.room.Index
import java.time.Instant

@Entity(indices = [Index("id")], primaryKeys = ["id"])
data class Show(
    val backdropPath: String?,
    val firstAirDate: Instant,
    val genreList: List<String>,
    val id: Long,
    val name: String,
    val originCountry: String?,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int,

    val updatedAt: Instant
)
