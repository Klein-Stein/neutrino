package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import io.github.kleinstein.neutrino.fabrics.Provider
import io.github.kleinstein.neutrino.fabrics.Singleton
import io.github.kleinstein.neutrino.fabrics.Stub
import io.github.kleinstein.neutrino.references.Weak
import kotlin.reflect.typeOf
import kotlin.test.*

class NeutrinoDITest {

    @Test
    fun buildingTest() {
        val module1 = Module("test1")
        val module2 = Module("test2")
        val di = NeutrinoDI {
            attachAll(module1, module2)
        }
        assertTrue(di.isEmpty())
        assertEquals(0, di.size)
        di.build()
        assertTrue(di.isNotEmpty())
        assertEquals(2, di.size)
        assertTrue { di.contains("test1") }
        assertTrue { di.contains("test2") }
    }

    @Test
    fun gettingModuleTest() {
        val module1 = Module("test1")
        val module2 = Module("test2")
        val di = NeutrinoDI {
            attachAll(module1, module2)
        }.build()
        assertEquals(module1, di["test1"])
        assertEquals(module2, di["test2"])
        assertFailsWith<NeutrinoException> {
            di["test3"]
        }
    }

    @Test
    fun detachingTest() {
        val module1 = Module("test1")
        val module2 = Module("test2")
        val di = NeutrinoDI {
            attachAll(module1, module2)
        }.build()
        assertTrue(di.isNotEmpty())
        assertEquals(2, di.size)
        assertEquals(module1, di.detach("test1"))
        assertTrue(di.isNotEmpty())
        assertEquals(1, di.size)
        assertEquals(module2, di.detach("test2"))
        assertTrue(di.isEmpty())
        assertEquals(0, di.size)
        assertNull(di.detach("test3"))
    }

    @Test
    fun resolvingTest() {
        val stub3 = Stub("stub3")
        val module1 = Module("module1") {
            addFabric(Key(type = typeOf<Stub>(), tag = "stub1"), Singleton { Stub("stub1") })
            addFabric(Key(type = typeOf<Stub>(), tag = "stub2"), Provider { Stub("stub2") })
            addFabric(Key(type = typeOf<Weak<Stub>>(), tag = "stub3"), Singleton { Weak(stub3) })
        }
        val module2 = Module("module2") {
            addFabric(Key(type = typeOf<Stub>(), tag = "stub4"), Singleton { Stub("stub4") })
        }
        val di = NeutrinoDI {
            attachAll(module1, module2)
        }.build()
        val stub1 = di.resolve<Stub>(typeOf<Stub>(), "stub1")
        val stub2 = di.resolve<Stub>(typeOf<Stub>(), "stub2")
        val stub3Ref = di.resolve<Weak<Stub>>(typeOf<Weak<Stub>>(), "stub3")
        val stub4 = di.resolve<Stub>(typeOf<Stub>(), "stub4")
        assertEquals("stub1", stub1.name)
        assertEquals("stub2", stub2.name)
        assertEquals("stub3", stub3Ref.get()!!.name)
        assertEquals("stub3", stub3.name) // To keep `stub3` in memory
        assertEquals("stub4", stub4.name)
    }
}