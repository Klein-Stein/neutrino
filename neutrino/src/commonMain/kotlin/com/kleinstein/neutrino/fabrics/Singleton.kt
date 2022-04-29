package com.kleinstein.neutrino.fabrics

class Singleton<T: Any>(fabric: () -> T): IFabric<T> {
    private val instance = fabric()

    override fun buildOrGet(): T = instance
}
