package jdr.tv.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(itemList: List<T>): List<Long>

    @Update
    abstract suspend fun update(item: T)

    @Update
    abstract suspend fun update(itemList: List<T>)

    @Delete
    abstract suspend fun delete(item: T)
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