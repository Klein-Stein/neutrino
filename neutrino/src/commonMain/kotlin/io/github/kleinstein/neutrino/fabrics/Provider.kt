package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.IFabric

class Provider<T: Any>(private val fabric: () -> T): IFabric<T> {
    override fun buildOrGet(): T = fabric()
}
