package io.github.kleinstein.neutrino.references

import io.github.kleinstein.neutrino.fabrics.Stub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeakTest {

    @Test
    fun clearTest() {
        val stub = Stub("test")
        val ref = Weak(stub)
        assertTrue(ref.get() != null)
        ref.clear()
        assertTrue(ref.get() == null)
        assertEquals("test", stub.name) // To keep the object in memory
    }
}