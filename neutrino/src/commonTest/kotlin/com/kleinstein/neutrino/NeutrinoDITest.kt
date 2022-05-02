package com.kleinstein.neutrino

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NeutrinoDITest {

    @Test
    fun buildingTest() {
        val injector1 = Injector("injector1") {
            attachAll(Module("test1") {})
        }
        val injector2 = Injector("injector2") {
            attachAll(Module("test1") {})
        }
        val di = NeutrinoDI {
            attachAll(injector1, injector2)
        }
        assertTrue(di.isEmpty())
        assertEquals(0, di.size)
        di.build()
        assertTrue(di.isNotEmpty())
        assertEquals(2, di.size)
        assertTrue { di.contains("injector1") }
        assertTrue { di.contains("injector2") }
    }

    @Test
    fun gettingInjectorTest() {
        val injector1 = Injector("injector1") {}
        val injector2 = Injector("injector2") {}
        val di = NeutrinoDI {
            attachAll(injector1, injector2)
        }.build()
        assertEquals(injector1, di["injector1"])
        assertEquals(injector2, di["injector2"])
    }

    @Test
    fun detachingTest() {
        val injector1 = Injector("injector1") {}
        val injector2 = Injector("injector2") {}
        val di = NeutrinoDI {
            attachAll(injector1, injector2)
        }.build()
        assertTrue(di.isNotEmpty())
        assertEquals(2, di.size)
        assertEquals(injector1, di.detach("injector1"))
        assertTrue(di.isNotEmpty())
        assertEquals(1, di.size)
        assertEquals(injector2, di.detach("injector2"))
        assertTrue(di.isEmpty())
        assertEquals(0, di.size)
    }
}