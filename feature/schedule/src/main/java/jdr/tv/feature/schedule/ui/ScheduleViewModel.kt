package jdr.tv.feature.schedule.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jdr.tv.common.extensions.conflateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ScheduleViewModel @Inject constructor(repository: ScheduleRepository) : ViewModel() {

    private val _watchList: BroadcastChannel<List<Model>> = repository.selectSchedule().conflateIn(viewModelScope)
    val watchList: Flow<List<Model>> = _watchList.asFlow()
}
