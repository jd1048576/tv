package jdr.tv.data

import kotlinx.coroutines.delay
import java.time.Instant

object Bucket {

    private const val MAX_REQUESTS = 40L

    private val lock = Any()
    private var lastUpdate = 0L
    private var tokens = 0L

    suspend fun throttle() {
        var delay = 0L

        synchronized(lock) {
            updateTokens()
            if (tokens == 0L) {
                delay = 1000L
            } else {
                tokens--
            }
        }

        if (delay != 0L) {
            delay(delay)
            throttle()
        }
    }

    private fun updateTokens() {
        val now = Instant.now().epochSecond
        val tokensToAdd = ((now - lastUpdate) * 4).coerceAtMost(MAX_REQUESTS)
        if (tokensToAdd != 0L) {
            tokens += tokensToAdd
            lastUpdate = now
        }
    }
}
