package jdr.tvtracker.data.remote

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DiscoverQueryBuilder {
    // TODO WHEN FIXED parameters.put("without_keywords", "191498");
    private val parameters: MutableMap<String, String> = mutableMapOf(
        "include_null_first_air_dates" to "false",
        "with_original_language" to "en",
        "without_genres" to "99|10762|10763|10764|10766|10767",
        "sort_by" to Sort.POPULARITY_DESC.value()
    )

    fun sortBy(sort: Sort): DiscoverQueryBuilder {
        parameters["sort_by"] = sort.value()
        return this
    }

    fun airDateGreaterThanOrEqualTo(instant: Instant): DiscoverQueryBuilder {
        parameters["air_date.gte"] = instantToUTCString(instant)
        return this
    }

    fun airDateLessThanOrEqualTo(instant: Instant): DiscoverQueryBuilder {
        parameters["air_date.lte"] = instantToUTCString(instant)
        return this
    }

    fun firstAirDateGreaterThanOrEqualTo(instant: Instant): DiscoverQueryBuilder {
        parameters["first_air_date.gte"] = instantToUTCString(instant)
        return this
    }

    fun firstAirDateLessThanOrEqualTo(instant: Instant): DiscoverQueryBuilder {
        parameters["first_air_date.lte"] = instantToUTCString(instant)
        return this
    }

    fun firstAirDateYear(year: Int): DiscoverQueryBuilder {
        parameters["first_air_date_year"] = year.toString()
        return this
    }

    fun voteAverageGreaterThanOrEqualTo(voteAverage: Double): DiscoverQueryBuilder {
        parameters["vote_average.gte"] = voteAverage.toString()
        return this
    }

    fun voteCountGreaterThanOrEqualTo(voteCount: Int): DiscoverQueryBuilder {
        parameters["vote_count.gte"] = voteCount.toString()
        return this
    }

    fun build(): Map<String, String> {
        return parameters
    }

    private fun instantToUTCString(instant: Instant): String {
        return DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.of("UTC")).format(instant)
    }

    enum class Sort(private val value: String) {

        VOTE_AVERAGE_DESC("vote_average.desc"),
        VOTE_AVERAGE_ASC("vote_average.asc"),
        FIRST_AIR_DATE_DESC("first_air_date.desc"),
        FIRST_AIR_DATE_ASC("first_air_date.asc"),
        POPULARITY_DESC("popularity.desc"),
        POPULARITY_ASC("popularity.asc");

        fun value(): String {
            return value
        }
    }

    companion object {
        fun topRated(): Map<String, String> = DiscoverQueryBuilder().sortBy(Sort.VOTE_AVERAGE_DESC).voteCountGreaterThanOrEqualTo(30).build()
        fun trending(): Map<String, String> = DiscoverQueryBuilder().build()
        fun upcoming(): Map<String, String> = DiscoverQueryBuilder().firstAirDateGreaterThanOrEqualTo(Instant.now()).build()
    }
}
