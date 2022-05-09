package io.github.kleinstein.neutrino.references

import io.github.kleinstein.neutrino.fabrics.Stub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeakReferenceTest {

    @Test
    fun clearTest() {
        val stub = Stub("test")
        val ref = WeakReference(stub)
        assertTrue(ref.get() != null)
        ref.clear()
        assertTrue(ref.get() == null)
        assertEquals("test", stub.name) // To keep the object in memory
    }
}