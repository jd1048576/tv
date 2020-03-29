@file:JvmName("SearchTextCreator")

package jdr.tv.feature.search.ui

import android.text.SpannableStringBuilder
import android.text.SpannedString
import jdr.tv.data.local.entities.Show
import java.time.Year
import java.time.ZoneId

fun details(show: Show): SpannedString = with(SpannableStringBuilder()) {
    append(Year.from(show.firstAirDate.atZone(ZoneId.systemDefault())).toString())
    if (show.genreList.isNotEmpty()) {
        append(" - ")
        append(show.genreList[0])
    }
    SpannedString(this)
}
