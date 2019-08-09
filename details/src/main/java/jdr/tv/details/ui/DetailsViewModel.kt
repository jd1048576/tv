package jdr.tv.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jdr.tv.base.Dispatchers.IO
import jdr.tv.data.Resource
import jdr.tv.local.entities.DetailedShow
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: DetailsRepository) : ViewModel() {

    private val _id = ConflatedBroadcastChannel<Long>()
    private val _show = ConflatedBroadcastChannel<Resource<DetailedShow>>()

    var id: Long
        get() = _id.value
        set(value) {
            _id.offer(value)
        }

    val show: Flow<Resource<DetailedShow>>
        get() = _show.asFlow()

    init {
        viewModelScope.launch {
            _id.asFlow()
                .distinctUntilChanged()
                .switchMap { repository.selectDetailedShow(it) }
                .flowOn(IO)
                .collect { _show.send(it) }
        }
    }
}
