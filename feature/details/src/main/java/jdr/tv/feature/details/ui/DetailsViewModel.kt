package jdr.tv.feature.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jdr.tv.common.extensions.conflateIn
import jdr.tv.common.ui.Resource
import jdr.tv.data.local.entities.DetailedSeason
import jdr.tv.data.local.entities.DetailedShow
import jdr.tv.data.local.entities.Show
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val showId: Long, private val repository: DetailsRepository) : ViewModel() {

    private val _show: ConflatedBroadcastChannel<Resource<Show>> = repository.selectShow(showId)
        .conflateIn(viewModelScope)
    private val _added: ConflatedBroadcastChannel<Boolean> = repository.selectAdded(showId)
        .conflateIn(viewModelScope, false)
    private val _detailedShow: ConflatedBroadcastChannel<DetailedShow> = repository.selectDetailedShow(showId)
        .conflateIn(viewModelScope)
    private val _detailedSeasonList: ConflatedBroadcastChannel<List<DetailedSeason>> = repository.selectDetailedSeasonList(showId)
        .conflateIn(viewModelScope)

    val show: Flow<Resource<Show>>
        get() = _show.asFlow()

    val added: Flow<Boolean>
        get() = _added.asFlow()

    val detailedShow: Flow<DetailedShow>
        get() = _detailedShow.asFlow()

    val detailedSeasonList: Flow<List<DetailedSeason>>
        get() = _detailedSeasonList.asFlow()

    fun updateAdded(added: Boolean) {
        viewModelScope.launch { repository.updateAdded(showId, added) }
    }

    fun updateSeasonWatched(detailedSeason: DetailedSeason) {
        viewModelScope.launch { repository.updateSeasonWatched(detailedSeason) }
    }
}
