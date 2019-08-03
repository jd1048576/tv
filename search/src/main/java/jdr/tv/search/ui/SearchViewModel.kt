package jdr.tv.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import jdr.tv.data.Resource
import jdr.tv.local.entities.Show
import jdr.tv.viewmodel.StateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : StateViewModel<SearchViewState>(SearchViewState()) {

    companion object {
        private const val DEBOUNCE = 275L
    }

    private var debounce: Job? = null
    private val invalidate = MutableLiveData<Unit>()

    var focus: Boolean
        get() = state.focus
        set(value) {
            state = state.copy(focus = value)
        }

    val search: LiveData<Resource<List<Show>>> = invalidate.switchMap { repository.search(state.query) }

    init {
        invalidate()
    }

    fun onQueryTextSubmit(query: String) {
        focus = false
        onQueryTextChange(query)
    }

    fun onQueryTextChange(query: String) {
        if (state.query != query) {
            state = state.copy(query = query)
            debounce?.cancel()
            debounce = viewModelScope.launch {
                delay(DEBOUNCE)
                if (query.isEmpty()) {
                    invalidate()
                } else {
                    invalidate.value = Unit
                }
            }
        }
    }

    private fun invalidate() = viewModelScope.launch { repository.deleteAll() }
}
