package io.github.kleinstein.neutrino.fabrics

import kotlin.test.*

class LazyProviderTest {

    @Test
    fun creationTest() {
        val fabric = LazyProvider { Stub() }
        val lazyS1 = fabric.buildOrGet()
        val lazyS2 = fabric.buildOrGet()
        val s1 by lazyS1
        val s2 by lazyS2
        assertFalse(lazyS1.isInitialized())
        assertFalse(lazyS2.isInitialized())
        assertNotEquals(s1, s2)
        assertTrue(lazyS1.isInitialized())
        assertTrue(lazyS2.isInitialized())
    }
}