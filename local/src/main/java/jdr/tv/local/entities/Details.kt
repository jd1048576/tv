package jdr.tv.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.Instant

@Entity(
    indices = [Index("showId")],
    primaryKeys = ["showId"],
    foreignKeys = [
        ForeignKey(
            entity = Show::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Details(
    val createdByList: List<CreatedBy>,
    val episodeRunTime: Int,
    val homepage: String,
    val showId: Long,
    val inProduction: Boolean,
    val lastAirDate: Instant,
    val networkList: List<Network>,
    val productionCompanyList: List<ProductionCompany>,
    val status: String,
    val type: String,

    // Extra Content
    val contentRatingList: List<ContentRating>,
    val castList: List<Cast>,
    val crewList: List<Crew>,
    @Embedded
    val externalIds: ExternalIds?,
    val videosList: List<Video>,

    val lastDetailsUpdate: Instant
)
