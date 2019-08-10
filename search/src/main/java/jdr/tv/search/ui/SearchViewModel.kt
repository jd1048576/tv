package jdr.tv.search.ui

import androidx.lifecycle.viewModelScope
import jdr.tv.base.Dispatchers.IO
import jdr.tv.data.Resource
import jdr.tv.local.entities.Show
import jdr.tv.viewmodel.StateViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : StateViewModel<SearchViewState>(SearchViewState()) {

    companion object {
        private const val DEBOUNCE = 275L
    }

    var previous: Resource<List<Show>>? = null

    private val invalidate = ConflatedBroadcastChannel<Unit>()
    private val _search = ConflatedBroadcastChannel<Resource<List<Show>>>()

    var focus: Boolean
        get() = state.focus
        set(value) {
            state = state.copy(focus = value)
        }

    val search: Flow<Resource<List<Show>>>
        get() = _search.asFlow()

    init {
        invalidate()
        viewModelScope.launch {
            invalidate.asFlow()
                .debounce(DEBOUNCE)
                .flatMapLatest { repository.search(state.query) }
                .flowOn(IO)
                .collect { previous = it; _search.send(it) }
        }
    }

    fun onQueryTextSubmit(query: String) {
        focus = false
        onQueryTextChange(query)
    }

    fun onQueryTextChange(query: String) {
        if (state.query != query) {
            state = state.copy(query = query)
            if (query.isEmpty()) {
                invalidate()
            } else {
                invalidate.offer(Unit)
            }
        }
    }

    private fun invalidate() = viewModelScope.launch { repository.deleteAll() }
}
