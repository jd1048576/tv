package jdr.tv.common.navigation

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

object GlobalActions {

    fun actionDetails(showId: Long): NavDirections {
        return ActionDetails(showId)
    }

    fun actionSearch(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_search)
    }

    fun actionSettings(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_settings)
    }

    data class ActionDetails(val showId: Long) : NavDirections {
        override fun getActionId(): Int {
            return R.id.action_details
        }

        override fun getArguments(): Bundle {
            return Bundle().apply {
                putLong("SHOW_ID", showId)
            }
        }

        companion object {
            fun fromBundle(bundle: Bundle?): ActionDetails {
                return ActionDetails(bundle!!.getLong("SHOW_ID"))
            }
        }
    }
}
