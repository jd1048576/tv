package jdr.tv.common.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.conflateIn(scope: CoroutineScope, initial: T? = null): BroadcastChannel<T> {
    return scope.broadcast(capacity = Channel.CONFLATED) {
        initial?.also { send(it) }
        collect { channel.send(it) }
    }
}
