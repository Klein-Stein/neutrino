package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.IFabric

/**
 * [Fabric][IFabric] that implements DI provider pattern
 *
 * The providers use the [fabric] to create a new instance of the injectable dependency each time
 * [buildOrGet] is called
 *
 * @param T Injectable dependency type
 * @param fabric Initialization block of the injectable dependency
 * @constructor Creates a new provider
 */
class Provider<T: Any>(private val fabric: () -> T): IFabric<T> {
    override fun buildOrGet(): T = fabric()
}
