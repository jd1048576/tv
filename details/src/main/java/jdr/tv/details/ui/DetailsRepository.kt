package jdr.tv.details.ui

import jdr.tv.local.Database
import jdr.tv.remote.TmdbApi
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val database: Database, private val tmdbApi: TmdbApi)