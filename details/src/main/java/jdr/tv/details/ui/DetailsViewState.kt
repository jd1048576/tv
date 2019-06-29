package jdr.tv.details.ui

import android.os.Bundle
import jdr.tv.viewmodel.State

data class DetailsViewState(val showId: Long = 0) : State<DetailsViewState> {

    override fun restore(bundle: Bundle): DetailsViewState {
        return DetailsViewState(bundle.getLong("SHOW_ID"))
    }

    override fun save(bundle: Bundle) {
        bundle.putLong("SHOW_ID", showId)
    }
}
