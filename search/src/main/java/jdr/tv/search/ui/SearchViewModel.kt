package jdr.tv.search.ui

import androidx.lifecycle.viewModelScope
import jdr.tv.base.extensions.conflateIn
import jdr.tv.data.Resource
import jdr.tv.local.entities.Show
import jdr.tv.viewmodel.StateViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : StateViewModel<SearchViewState>(SearchViewState()) {

    companion object {
        private const val DEBOUNCE = 275L
    }

    private val invalidate = ConflatedBroadcastChannel<Unit>()
    private val _search: ConflatedBroadcastChannel<Resource<List<Show>>> = invalidate.asFlow()
        .debounce(DEBOUNCE)
        .flatMapLatest { repository.search(state.query) }
        .conflateIn(viewModelScope)

    var focus: Boolean
        get() = state.focus
        set(value) {
            state = state.copy(focus = value)
        }

    val search: Flow<Resource<List<Show>>>
        get() = _search.asFlow()

    fun onQueryTextSubmit(query: String) {
        focus = false
        onQueryTextChange(query)
    }

    fun onQueryTextChange(query: String) {
        val trimmedQuery = query.trim()
        if (state.query != trimmedQuery) {
            state = state.copy(query = trimmedQuery)
            if (trimmedQuery.isEmpty()) {
                invalidate()
            } else {
                invalidate.offer(Unit)
            }
        }
    }

    private fun invalidate() = viewModelScope.launch { repository.deleteAll() }
}
