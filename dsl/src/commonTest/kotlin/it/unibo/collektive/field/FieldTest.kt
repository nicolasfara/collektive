package it.unibo.collektive.field

import it.unibo.collektive.ID
import it.unibo.collektive.IntId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FieldTest {
    private val myId: ID = IntId()
    private val myValue: String = "myValue"
    private val connectedId: ID = IntId()
    private val connectedValue: String = "connectedValue"

    @Test
    fun createFieldWithoutMessages() {
        val field = Field(myId, myValue, emptyMap())
        assertTrue(field.toMap().containsKey(myId))
        assertEquals(1, field.toMap().size)
    }

    @Test
    fun createFieldWithMessages() {
        val field = Field(myId, myValue, mapOf(connectedId to connectedValue))
        assertTrue(field.toMap().containsKey(myId))
        assertTrue(field.toMap().containsKey(connectedId))
        assertEquals(2, field.toMap().size)
    }

    @Test
    fun getFieldValueById() {
        val field = Field(myId, myValue, mapOf(connectedId to connectedValue))
        assertEquals(myValue, field[myId])
        assertEquals(connectedValue, field[connectedId])
    }
}
