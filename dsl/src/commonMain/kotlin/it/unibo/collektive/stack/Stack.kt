package it.unibo.collektive.stack

class Stack<X> {
    private val currentStack = ArrayDeque<X?>()

    fun currentPath(): Path = Path(currentStack.toList())
    fun alignRaw(token: X?) = currentStack.addLast(token)
    fun dealign() = currentStack.removeLast()

    override fun toString(): String = currentStack.toString()
}

data class Path(val path: List<Any?>)
