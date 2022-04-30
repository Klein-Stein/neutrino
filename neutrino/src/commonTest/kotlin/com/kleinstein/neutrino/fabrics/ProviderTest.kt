package com.kleinstein.neutrino.fabrics

import kotlin.test.Test
import kotlin.test.assertNotEquals

class ProviderTest {

    @Test
    fun creationTest() {
        val provider = Provider { Stub() }
        val stub1 = provider.buildOrGet()
        val stub2 = provider.buildOrGet()
        assertNotEquals(stub1, stub2)
    }
}