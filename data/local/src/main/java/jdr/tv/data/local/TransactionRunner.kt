package jdr.tv.data.local

interface TransactionRunner {
    suspend fun <R> withTransaction(block: suspend () -> R): R
}
