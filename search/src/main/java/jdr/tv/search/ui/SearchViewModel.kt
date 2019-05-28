package jdr.tv.search.ui

import androidx.lifecycle.ViewModel

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    lateinit var state: SearchViewState

    fun updateFocus(focus: Boolean) {
        state = state.copy(focus = focus)
    }

    fun submitQuery(query: String) {
        updateFocus(false)
        onQueryTextChange(query)
    }

    fun onQueryTextChange(query: String) {
        if (state.query != query) {
            state = state.copy(query = query)
        }
    }
}