package jdr.tv.feature.watchlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jdr.tv.common.extensions.conflateIn
import jdr.tv.data.local.entities.EpisodeItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class WatchListViewModel @Inject constructor(repository: WatchListRepository) : ViewModel() {

    private val _watchList: BroadcastChannel<List<EpisodeItem>> = repository.selectWatchList().conflateIn(viewModelScope)
    val watchList: Flow<List<EpisodeItem>> = _watchList.asFlow()
}
