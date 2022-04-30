package com.kleinstein.neutrino

import com.kleinstein.neutrino.fabrics.LazySingleton
import com.kleinstein.neutrino.fabrics.Singleton
import com.kleinstein.neutrino.fabrics.Stub
import kotlin.test.*

class ModuleTest {

    @Test
    fun nameTest() {
        val name = "test"
        val module = Module(name) {}
        assertEquals(name, module.name)
    }

    @Test
    fun buildingTest() {
        val module = Module("test") {
            addFabric("stub1", Singleton { Stub() })
            addFabric("stub2", Singleton { Stub() })
        }
        assertEquals(0, module.fabrics.size)
        module.build()
        assertEquals(2, module.fabrics.size)
        assertTrue { module.fabrics.containsKey("stub1") }
        assertTrue { module.fabrics.containsKey("stub2") }
    }

    @Test
    fun resolvingTest() {
        val module = Module("test") {
            addFabric("stub1", LazySingleton { Stub() })
            addFabric("stub2", Singleton { Stub() })
        }.build()
        val lazyStubSingleton1 = module.resolveLazy("stub1", Stub::class)
        val stub1 by lazyStubSingleton1
        val stub2 = module.resolve("stub2", Stub::class)
        assertFalse(lazyStubSingleton1.isInitialized())
        assertNotEquals(stub1, stub2)
        assertTrue(lazyStubSingleton1.isInitialized())
    }
}