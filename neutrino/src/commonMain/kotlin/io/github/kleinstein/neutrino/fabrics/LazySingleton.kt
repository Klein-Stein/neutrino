package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.ILazyFabric

class LazySingleton<T: Any>(fabric: () -> T) : ILazyFabric<T> {
    private val instance = lazy(fabric)

    override fun buildOrGet(): Lazy<T> = instance
}
