package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.IFabric

class Singleton<T: Any>(fabric: () -> T): IFabric<T> {
    private val instance = fabric()

    override fun buildOrGet(): T = instance
}
