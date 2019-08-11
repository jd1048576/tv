package jdr.tv.base.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.broadcastIn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.conflateIn(scope: CoroutineScope): ConflatedBroadcastChannel<T> {
    val channel = ConflatedBroadcastChannel<T>()
    scope.launch {
        broadcastIn(scope).apply {
            invokeOnClose {
                channel.close(it)
            }
            asFlow().collect { channel.send(it) }
        }
    }
    return channel
}
