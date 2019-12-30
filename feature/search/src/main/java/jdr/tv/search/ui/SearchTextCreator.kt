@file:JvmName("SearchTextCreator")

package jdr.tv.search.ui

import android.text.SpannableStringBuilder
import android.text.SpannedString
import java.time.Year
import jdr.tv.base.extensions.SYSTEM_ZONE_ID
import jdr.tv.local.entities.Show

fun details(show: Show?): SpannedString = with(SpannableStringBuilder()) {
    if (show != null) {
        append(Year.from(show.firstAirDate.atZone(SYSTEM_ZONE_ID)).toString())
        if (show.genreList.isNotEmpty()) {
            append(" - ")
            append(show.genreList[0])
        }
    }
    SpannedString(this)
}
