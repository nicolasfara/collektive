package it.unibo.collektive.field

import it.unibo.collektive.ID

interface Field<out T> : Map<ID, T> {
    val localId: ID
    val local: T?
    fun excludeSelf(): Field<T>

    companion object {
        operator fun <T> invoke(localId: ID, local: T, messages: Map<ID, T>): Field<T> =
            FieldWithSelf(localId, local, messages)

        operator fun <T> invoke(localId: ID, messages: Map<ID, T>): Field<T> =
            FieldWithoutSelf(localId, messages)
    }
}

internal data class FieldWithoutSelf<T>(
    override val localId: ID,
    private val messages: Map<ID, T> = emptyMap(),
) : Field<T>, Map<ID, T> by messages {
    override val local: T? = null
    override fun excludeSelf(): Field<T> = this
}

internal data class FieldWithSelf<T>(
    override val localId: ID,
    override val local: T,
    private val messages: Map<ID, T> = emptyMap(),
) : Field<T>, Map<ID, T> by messages + (localId to local) {
    override fun excludeSelf(): Field<T> = FieldWithoutSelf(localId, this.filterKeys { it != localId }.toMap())
}

// interface Field<out T> {
//    val localId: ID
//    val messages: Map<ID, *>
//    fun toMap(): Map<ID, T>
//    fun getSelf(): Map<ID, T>
//    fun excludeSelf(): Map<ID, T>
//    operator fun get(id: ID): T
// }
//
// @Suppress("UNCHECKED_CAST")
// class FieldImpl<T>(override val localId: ID, override val messages: Map<ID, *> = emptyMap<ID, T>()) : Field<T> {
//
//    private val field: Map<ID, T> = messages as Map<ID, T>
//    override fun get(id: ID): T = field[id]
//        ?: throw IllegalArgumentException("No value found for the specified id")
//
//    override fun toMap(): Map<ID, T> = field.toMap()
//    override fun getSelf(): Map<ID, T> = messages.filterKeys { it == localId } as Map<ID, T>
//
//    override fun excludeSelf(): Map<ID, T> = messages.filterKeys { it != localId } as Map<ID, T>
//
//    override fun toString(): String {
//        return "FieldImpl(field=$field)"
//    }
// }
