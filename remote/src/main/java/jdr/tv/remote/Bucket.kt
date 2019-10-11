package jdr.tv.remote

import java.time.Instant
import kotlinx.coroutines.delay

object Bucket {

    private const val MAX_TOKENS = 40L
    private const val TOKENS_PER_SECOND = 4
    private const val DELAY = 1000L

    private val lock = Any()
    private var lastUpdate = 0L
    private var tokens = 0L

    suspend fun throttle() {
        var shouldDelay = false

        synchronized(lock) {
            updateTokens()
            if (tokens == 0L) {
                shouldDelay = true
            } else {
                tokens--
            }
        }

        if (shouldDelay) {
            delay(DELAY)
            throttle()
        }
    }

    private fun updateTokens() {
        val now = Instant.now().epochSecond
        val tokensToAdd = ((now - lastUpdate) * TOKENS_PER_SECOND).coerceAtMost(MAX_TOKENS)
        if (tokensToAdd != 0L) {
            tokens += tokensToAdd
            lastUpdate = now
        }
    }
}
