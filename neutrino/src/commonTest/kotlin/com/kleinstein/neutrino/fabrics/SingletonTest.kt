package com.kleinstein.neutrino.fabrics

import kotlin.test.Test
import kotlin.test.assertEquals

class SingletonTest {

    @Test
    fun creationTest() {
        val fabric = Singleton { Stub() }
        val s1 = fabric.buildOrGet()
        val s2 = fabric.buildOrGet()
        assertEquals(s1, s2)
    }
}