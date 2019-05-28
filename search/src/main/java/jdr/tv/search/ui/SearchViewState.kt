package jdr.tv.search.ui

import android.os.Bundle

data class SearchViewState(val query: String, val focus: Boolean) {
    constructor(bundle: Bundle?) : this(bundle?.getString("QUERY").orEmpty(), bundle?.getBoolean("FOCUS") ?: true)

    fun save(bundle: Bundle) = with(bundle) {
        putString("QUERY", query)
        putBoolean("FOCUS", focus)
    }
}
