package jdr.tv.search.ui

import android.text.SpannableStringBuilder
import android.text.SpannedString
import jdr.tv.base.extensions.SYSTEM_ZONE_ID
import jdr.tv.local.entities.Show
import java.time.Year

object SearchTextCreator {

    @JvmStatic
    fun details(show: Show?): SpannedString = with(SpannableStringBuilder()) {
        if (show != null) {
            append(Year.from(show.firstAirDate.atZone(SYSTEM_ZONE_ID)).toString())
            append(" ")
            if (show.genreList.isNotEmpty()) {
                append(" - ")
                append(show.genreList[0])
            }
        }
        SpannedString(this)
    }
}