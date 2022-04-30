package com.kleinstein.neutrino

import com.kleinstein.neutrino.fabrics.LazySingleton
import com.kleinstein.neutrino.fabrics.Singleton
import com.kleinstein.neutrino.fabrics.Stub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InjectorTest {

    @Test
    fun nameTest() {
        val name = "test"
        val injector = Injector(name) {}
        assertEquals(name, injector.name)
    }

    @Test
    fun buildingTest() {
        val test1Module = Module("test1") {
            addFabric("stub1", Singleton { Stub() })
            addFabric("stub2", Singleton { Stub() })
        }
        val test2Module = Module("test2") {
            addFabric("stub1", LazySingleton { Stub() })
            addFabric("stub2", Singleton { Stub() })
        }
        val injector = Injector("test") {
            importAll(test1Module, test2Module)
        }
        assertTrue(injector.isEmpty())
        assertEquals(0, injector.size)
        injector.build()
        assertTrue(injector.isNotEmpty())
        assertEquals(2, injector.size)
        assertTrue { injector.containsModuleName("test1") }
        assertTrue { injector.containsModuleName("test2") }
    }
}