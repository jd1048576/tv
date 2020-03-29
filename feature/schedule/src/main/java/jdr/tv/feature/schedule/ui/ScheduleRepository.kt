package jdr.tv.feature.schedule.ui

import jdr.tv.data.local.Database
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class ScheduleRepository @Inject constructor(private val database: Database) {

    fun selectSchedule(): Flow<List<Model>> {
        return database.episodeDao().selectScheduleList().flowOn(IO).map { scheduleList ->
            val zoneId: ZoneId = ZoneId.systemDefault()
            val max: LocalDate = LocalDate.now().plusWeeks(1)
            scheduleList.groupBy { minOf(it.airDate.atZone(zoneId).toLocalDate(), max) }
                .entries
                .sortedBy { it.key }
                .map { (day, list) ->
                    ArrayList<Model>().apply {
                        add(TitleModel(day.takeUnless { it == max }?.format(DateTimeFormatter.ofPattern("EEEE")) ?: "Later", -day.toEpochDay()))
                        addAll(list.map { EpisodeModel(it) }.sortedBy { it.episode.airDate })
                    }
                }.flatten()
        }
    }
}
