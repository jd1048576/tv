package jdr.tv.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jdr.tv.base.extensions.conflateIn
import jdr.tv.data.Resource
import jdr.tv.local.entities.DetailedShow
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class DetailsViewModel(showId: Long, repository: DetailsRepository) : ViewModel() {

    private val _detailedShow: ConflatedBroadcastChannel<Resource<DetailedShow>> = repository.selectDetailedShow(showId)
        .conflateIn(viewModelScope)

    val detailedShow: Flow<Resource<DetailedShow>>
        get() = _detailedShow.asFlow()
}
