package jdr.tv.search.ui

import android.os.Bundle
import jdr.tv.viewmodel.State

data class SearchViewState(val query: String, val focus: Boolean) : State<SearchViewState> {

    override fun restore(bundle: Bundle): SearchViewState {
        return SearchViewState(bundle.getString("QUERY", ""), bundle.getBoolean("FOCUS", true))
    }

    override fun save(bundle: Bundle) = with(bundle) {
        putString("QUERY", query)
        putBoolean("FOCUS", focus)
    }
}
