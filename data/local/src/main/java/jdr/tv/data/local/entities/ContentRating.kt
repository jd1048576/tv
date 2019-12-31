package jdr.tv.data.local.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContentRating(
    val countryCode: String,
    val rating: String
) {

    class Rating(val name: String, val minAge: Double, val aliasList: List<String> = emptyList())

    companion object {

        private val COUNTRY_RATING_MAP = mapOf(
            "AU" to listOf(
                Rating("G", 0.0, listOf("P", "C")),
                Rating("PG", 7.5),
                Rating("M", 15.0),
                Rating("MA15+", 16.5, listOf("AV15+")),
                Rating("R18+", 18.0)
            ),
            "CA" to listOf(
                Rating("G", 0.0, listOf("C", "Exempt")),
                Rating("PG", 8.0, listOf("C8")),
                Rating("14+", 14.0),
                Rating("18+", 18.0)
            ),
            "GB" to listOf(
                Rating("U", 0.0),
                Rating("PG", 8.0),
                Rating("12", 12.0, listOf("12A")),
                Rating("15", 15.0),
                Rating("18", 18.0, listOf("R18"))
            ),
            "US" to listOf(
                Rating("TV-G", 0.0, listOf("TV-Y", "NR")),
                Rating("TV-PG", 7.0, listOf("TV-Y7")),
                Rating("TV-14", 14.0),
                Rating("TV-MA", 17.0)
            )
        )

        fun getRatingFromList(countryCode: String, contentRatingList: List<ContentRating>): String {
            return contentRatingList
                .find { it.countryCode == countryCode }
                ?.rating
                ?: calculateRating(countryCode, contentRatingList)
        }

        private fun calculateRating(countryCode: String, contentRatingList: List<ContentRating>): String {
            return COUNTRY_RATING_MAP[countryCode]?.let { ratingList ->
                contentRatingList
                    .asSequence()
                    .filter { COUNTRY_RATING_MAP.containsKey(it.countryCode) }
                    .map {
                        toMinAge(
                            it.rating,
                            COUNTRY_RATING_MAP.getValue(it.countryCode)
                        )
                    }
                    .average()
                    .takeIf { it.isFinite() }
                    ?.let { "Calculated Rating " + toRatingString(
                        it,
                        ratingList
                    )
                    }
            } ?: "Content Rating Unavailable"
        }

        private fun toRatingString(age: Double, ratingList: List<Rating>): String {
            return ratingList
                .filter { it.minAge >= age }
                .minBy { it.minAge }
                .let { it!!.name }
        }

        private fun toMinAge(contentRating: String, ratingList: List<Rating>): Double {
            return ratingList
                .first { it.name == contentRating || it.aliasList.contains(contentRating) }
                .let { it.minAge }
        }
    }
}
