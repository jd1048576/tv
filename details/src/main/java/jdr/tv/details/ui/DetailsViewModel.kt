package jdr.tv.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jdr.tv.base.extensions.conflateIn
import jdr.tv.data.Resource
import jdr.tv.local.entities.DetailedSeason
import jdr.tv.local.entities.DetailedShow
import jdr.tv.local.entities.Show
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class DetailsViewModel(showId: Long, private val repository: DetailsRepository) : ViewModel() {

    private val _show: ConflatedBroadcastChannel<Resource<Show>> = repository.selectShow(showId)
        .conflateIn(viewModelScope)
    private val _detailedShow: ConflatedBroadcastChannel<DetailedShow> = repository.selectDetailedShow(showId)
        .conflateIn(viewModelScope)
    private val _detailedSeasonList: ConflatedBroadcastChannel<List<DetailedSeason>> = repository.selectDetailedSeasonList(showId)
        .conflateIn(viewModelScope)

    val show: Flow<Resource<Show>>
        get() = _show.asFlow()

    val detailedShow: Flow<DetailedShow>
        get() = _detailedShow.asFlow()

    val detailedSeasonList: Flow<List<DetailedSeason>>
        get() = _detailedSeasonList.asFlow()

    fun updateSeasonWatched(detailedSeason: DetailedSeason) {
        viewModelScope.launch { repository.updateSeasonWatched(detailedSeason) }
    }
}
