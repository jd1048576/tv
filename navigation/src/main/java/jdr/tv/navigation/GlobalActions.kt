package jdr.tv.navigation

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

object GlobalActions {
    fun actionSearch(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_search)
    }

    fun actionSettings(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_settings)
    }
}
