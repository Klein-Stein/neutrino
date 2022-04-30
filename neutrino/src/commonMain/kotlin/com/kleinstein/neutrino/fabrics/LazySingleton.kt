package com.kleinstein.neutrino.fabrics

import com.kleinstein.neutrino.contracts.ILazyFabric

class LazySingleton<T: Any>(fabric: () -> T) : ILazyFabric<T> {
    private val instance = lazy(fabric)

    override fun buildOrGet(): Lazy<T> = instance
}
