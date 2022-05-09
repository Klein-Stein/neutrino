package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.fabrics.Provider
import io.github.kleinstein.neutrino.fabrics.Singleton
import io.github.kleinstein.neutrino.fabrics.Stub
import io.github.kleinstein.neutrino.fabrics.WeakSingleton
import io.github.kleinstein.neutrino.references.WeakReference
import kotlin.reflect.typeOf
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
            addFabric("stub2", Provider { Stub() })
            addFabric("stub3", WeakSingleton { Stub() })
        }
        assertTrue(module.isEmpty())
        assertEquals(0, module.size)
        module.build()
        assertTrue(module.isNotEmpty())
        assertEquals(2, module.size)
        assertTrue { module.contains("stub1") }
        assertTrue { module.contains("stub2") }
        assertTrue { module.contains("stub3") }
    }

    @Test
    fun resolvingTest() {
        val stub3 = Stub("stub3")
        val module = Module("test") {
            addFabric("stub1", Singleton { Stub() })
            addFabric("stub2", Provider { Stub() })
            addFabric("stub3", WeakSingleton { stub3 })
        }.build()
        val stub1 = module.resolve<Stub>(typeOf<Stub>(), "stub1")
        val stub2 = module.resolve<Stub>(typeOf<Stub>(), "stub2")
        val stub3Ref = module.resolve<WeakReference<Stub>>(
            typeOf<WeakReference<Stub>>(), "stub3"
        )
        assertEquals("stub1", stub1.name)
        assertEquals("stub2", stub2.name)
        assertEquals("stub3", stub3Ref.get()!!.name)
        assertEquals("stub3", stub3.name) // To keep `stub3` in memory
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
