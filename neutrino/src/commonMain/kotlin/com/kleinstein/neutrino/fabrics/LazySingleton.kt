package com.kleinstein.neutrino.fabrics

class LazySingleton<T: Any>(fabric: () -> T) : IFabric<T> {
    private val instance by lazy(fabric)

    override fun buildOrGet(): T = instance
}
