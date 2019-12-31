package jdr.tv.data.remote.entities

class RemoteGenre(
    val name: String
) {
    companion object {
        @Suppress("MagicNumber")
        fun createFromId(id: Int?): RemoteGenre {
            checkNotNull(id) { "RemoteGenre Id Cannot be Null" }
            val name = when (id) {
                12, 28, 10759 -> "Action & Adventure"
                16 -> "Animation"
                35 -> "Comedy"
                80 -> "Crime"
                99 -> "Documentary"
                18, 10749 -> "Drama"
                10751 -> "Family"
                10762 -> "Kids"
                27, 9648 -> "Mystery"
                10763 -> "News"
                10764 -> "Reality"
                14, 878, 10765 -> "Sci-Fi & Fantasy"
                10766 -> "Soap"
                10767 -> "Talk"
                10768 -> "War & Politics"
                37 -> "Western"
                else -> "Unknown Genre id $id"
            }
            return RemoteGenre(name)
        }
    }
}
