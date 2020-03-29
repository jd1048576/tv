package jdr.tv.feature.schedule.ui

import jdr.tv.data.local.entities.EpisodeItem

interface Model {
    val id: Long
}

class EpisodeModel(val episode: EpisodeItem, override val id: Long = episode.id) : Model
class TitleModel(val title: String, override val id: Long) : Model
