package jdr.tv.shows.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jdr.tv.base.extensions.conflateIn
import jdr.tv.local.entities.Show
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class ShowsViewModel(repository: ShowsRepository) : ViewModel() {

    private val _addedShowList: ConflatedBroadcastChannel<List<Show>> = repository.selectAddedShowList()
        .conflateIn(viewModelScope)

    val addedShowList: Flow<List<Show>>
        get() = _addedShowList.asFlow()
}
