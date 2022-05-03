package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.fabrics.LazySingleton
import io.github.kleinstein.neutrino.fabrics.Singleton
import io.github.kleinstein.neutrino.fabrics.Stub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
            attachAll(test1Module, test2Module)
        }
        assertTrue(injector.isEmpty())
        assertEquals(0, injector.size)
        injector.build()
        assertTrue(injector.isNotEmpty())
        assertEquals(2, injector.size)
        assertTrue { injector.contains("test1") }
        assertTrue { injector.contains("test2") }
    }

    @Test
    fun resolvingTest() {
        val test1Module = Module("test1") {
            addFabric("stub1", Singleton { Stub("stub1") })
        }
        val test2Module = Module("test2") {
            addFabric("stub1", Singleton { Stub("stub2") })
            addFabric("lazyStub", LazySingleton { Stub("lazyStub") })
        }
        val injector = Injector("test") {
            attachAll(test1Module, test2Module)
        }.build()
        val stub1 = injector.resolve("stub1", Stub::class)
        assertEquals("stub1", stub1.name)
        val lazyStub = injector.resolveLazy("lazyStub", Stub::class)
        val stub3 by lazyStub
        assertFalse(lazyStub.isInitialized())
        assertEquals("lazyStub", stub3.name)
        assertTrue(lazyStub.isInitialized())
    }

    @Test
    fun detachingTest() {
        val test1Module = Module("test1") {}
        val test2Module = Module("test2") {}
        val injector = Injector("test") {
            attachAll(test1Module, test2Module)
        }.build()
        assertTrue(injector.isNotEmpty())
        assertEquals(2, injector.size)
        assertEquals(test1Module, injector.detach("test1"))
        assertTrue(injector.isNotEmpty())
        assertEquals(1, injector.size)
        assertEquals(test2Module, injector.detach("test2"))
        assertTrue(injector.isEmpty())
        assertEquals(0, injector.size)
    }
}