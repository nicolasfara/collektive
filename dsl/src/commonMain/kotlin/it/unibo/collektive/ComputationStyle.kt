package it.unibo.collektive

import it.unibo.collektive.stack.Path

fun <X> singleCycle(
    localId: ID,
    messages: Map<ID, Map<Path, *>>,
    state: Map<Path, *>,
    compute: AggregateContext.() -> X
): AggregateContext.AggregateResult<X> {
    return with(AggregateContext(localId, messages, state)) {
        AggregateContext.AggregateResult(compute(), messagesToSend(), newState())
    }
}

fun <X> runUntil(
    condition: () -> Boolean,
    network: Network,
    compute: AggregateContext.() -> X
): AggregateContext.AggregateResult<X> {
    val localId: ID = IntId()
    var state = emptyMap<Path, Any?>()
    var computed: AggregateContext.AggregateResult<X>? = null
    while (condition()) {
        computed = singleCycle(localId, network.receive(), state, compute)
        state = computed.newState
        network.send(localId, computed.toSend)
    }
    return computed ?: error("The computation did not produce a result")
}
