package jdr.tv.search.ui

import jdr.tv.local.Database
import jdr.tv.remote.TmdbApi
import javax.inject.Inject

class SearchRepository @Inject constructor(private val database: Database, private val tmdbApi: TmdbApi)