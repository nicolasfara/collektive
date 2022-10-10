interface Field<out T>

class FieldImpl<T> : Field<T> {
    val field: MutableMap<Int, T> = mutableMapOf()
    fun addElement(id: Int, value: T): T? = field.put(id, value)
    fun getById(id: Int): T? = field[id]
    override fun toString(): String {
        return "FieldImpl(field=$field)"
    }
}