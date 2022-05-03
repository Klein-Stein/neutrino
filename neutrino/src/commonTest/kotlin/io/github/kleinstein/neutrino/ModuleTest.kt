package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.fabrics.LazySingleton
import io.github.kleinstein.neutrino.fabrics.Singleton
import io.github.kleinstein.neutrino.fabrics.Stub
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
        assertTrue(module.isEmpty())
        assertEquals(0, module.size)
        module.build()
        assertTrue(module.isNotEmpty())
        assertEquals(2, module.size)
        assertTrue { module.containsTag("stub1") }
        assertTrue { module.containsTag("stub2") }
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

    @Test
    fun removingTest() {
        val module = Module("test") {
            addFabric("stub1", Singleton { Stub() })
        }.build()
        assertTrue(module.isNotEmpty())
        module.removeFabric("stub1")
        assertTrue(module.isEmpty())
    }
}
