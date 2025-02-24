package it.unibo.collektive.branch

import it.unibo.collektive.aggregate
import kotlin.test.Test
import kotlin.test.assertTrue

class IfElseBlockTest {

    @Test
    fun trueConditionIfElseBlock() {
        val customCondition = true
        val result = aggregate {
            if (customCondition) {
                neighbouring("test")
            } else {
                neighbouring("test")
            }
        }
        assertTrue(result.toSend.keys.any {
            it.path.toString().contains("customCondition") &&
                    it.path.toString().contains("true")
        })
    }

    @Test
    fun falseConditionIfElseBlock() {
        val customCondition = false
        val result = aggregate {
            if (customCondition) {
                neighbouring("test")
            } else {
                neighbouring("test")
            }
        }
        assertTrue(result.toSend.keys.any {
            it.path.toString().contains("constant") &&
                    it.path.toString().contains("false")
        })
    }

    @Test
    fun ifElseBlock() {
        val customCondition1 = false
        val customCondition2 = true
        val result = aggregate {
            if (customCondition1) {
                neighbouring("test")
            } else if (customCondition2) {
                neighbouring("test")
            } else {
                neighbouring("test")
            }
        }
        assertTrue(result.toSend.keys.any {
            it.path.toString().contains("customCondition2") &&
                    it.path.toString().contains("true")
        })
    }
}
