package jdr.tv.base.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.broadcastIn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.conflateIn(scope: CoroutineScope, initial: T? = null): ConflatedBroadcastChannel<T> {
    val channel: ConflatedBroadcastChannel<T> = if (initial != null) ConflatedBroadcastChannel(initial) else ConflatedBroadcastChannel()
    val broadcastChannel = broadcastIn(scope).apply {
        invokeOnClose {
            channel.close(it)
        }
    }
    scope.launch {
        broadcastChannel.asFlow().collect { channel.send(it) }
    }
    return channel
}
