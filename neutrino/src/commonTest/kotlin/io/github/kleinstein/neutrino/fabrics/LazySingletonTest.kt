package io.github.kleinstein.neutrino.fabrics

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LazySingletonTest {

    @Test
    fun creationTest() {
        val fabric = LazySingleton { Stub() }
        val s1 by fabric.buildOrGet()
        val s2 by fabric.buildOrGet()
        assertFalse(fabric.buildOrGet().isInitialized())
        assertEquals(s1, s2)
        assertTrue(fabric.buildOrGet().isInitialized())
    }
}