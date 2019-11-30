package jdr.tv.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(itemList: List<T>): List<@JvmSuppressWildcards Long>

    @Update
    suspend fun update(item: T)

    @Update
    suspend fun update(itemList: List<T>)

    @Delete
    suspend fun delete(item: T)
}

suspend fun <T> BaseDao<T>.insertOrUpdate(item: T, updateFunction: suspend (T) -> Unit = { update(it) }) {
    val insertResult = insert(item)
    if (insertResult == -1L) {
        updateFunction(item)
    }
}

suspend fun <T> BaseDao<T>.insertOrUpdate(itemList: List<T>, updateFunction: suspend (List<T>) -> Unit = { update(it) }) {
    val insertResult = insert(itemList)
    val updateList = insertResult.asSequence().withIndex().filter { it.value == -1L }.map { itemList[it.index] }.toList()

    if (updateList.isNotEmpty()) {
        updateFunction(updateList)
    }
}
