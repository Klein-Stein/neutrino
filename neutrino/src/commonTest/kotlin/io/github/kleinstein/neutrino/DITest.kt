package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.fabrics.Stub
import io.github.kleinstein.neutrino.references.Weak
import kotlin.test.Test
import kotlin.test.assertEquals

class DITest {

    @Test
    fun resolvingTest() {
        val stub3 = Stub("stub3")
        val stub4 = Stub("stub4")
        val module1 = DI.module("module1") {
            singleton("stub1") { Stub("stub1") }
            provider("stub2") { Stub("stub2") }
            weakSingleton("stub3") { stub3 }
            weakSingleton { stub4 }
        }
        val module2 = DI.module("module2") {
            singleton("stub5") { Stub("stub5") }
        }
        DI.global.attachAll(module1, module2)
        val stub1 = DI.global.resolve<Stub>("stub1")
        val stub2 = DI.global.resolve<Stub>("stub2")
        val stub3Ref = DI.global.resolve<Weak<Stub>>("stub3")
        val stub4Ref = DI.global.resolve<Weak<Stub>>()
        val stub5 = DI.global.resolve<Stub>("stub5")
        assertEquals("stub1", stub1.name)
        assertEquals("stub2", stub2.name)
        assertEquals("stub3", stub3Ref.get()!!.name)
        assertEquals("stub4", stub4Ref.get()!!.name)
        assertEquals("stub3", stub3.name) // To keep `stub3` in memory
        assertEquals("stub4", stub4.name) // To keep `stub4` in memory
        assertEquals("stub5", stub5.name)
        // Detach modules
        DI.global.detach("module1")
        DI.global.detach("module2")
    }
}
