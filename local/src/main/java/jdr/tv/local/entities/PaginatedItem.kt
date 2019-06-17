package jdr.tv.local.entities

interface PaginatedItem {
    val id: Long
    val showId: Long
    val page: Int
}