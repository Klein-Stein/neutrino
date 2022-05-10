package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.IFabric

/**
 * [Fabric][IFabric] that implements DI singleton pattern
 *
 * The singletons use the [fabric] once to create a new instance of the injectable dependency when
 * [buildOrGet] is called, all next times they will return a reference to the earlier created
 * instance
 *
 * @param T Injectable dependency type
 * @param fabric Initialization block of the injectable dependency
 * @constructor Creates a new singleton
 */
class Singleton<T: Any>(private val fabric: () -> T): IFabric<T> {
    private lateinit var instance: T

    override fun buildOrGet(): T {
        if (!this::instance.isInitialized) {
            this.instance = this.fabric()
        }
        return this.instance
    }
}
