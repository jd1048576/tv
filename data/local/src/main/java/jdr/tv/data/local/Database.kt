package jdr.tv.data.local

interface Database : TransactionRunner {
    fun addDao(): AddDao
    fun detailsDao(): DetailsDao
    fun episodeDao(): EpisodeDao
    fun relatedShowDao(): RelatedShowDao
    fun showDao(): ShowDao
    fun searchItemDao(): SearchItemDao
    fun seasonDao(): SeasonDao
    fun watchDao(): WatchDao
}
