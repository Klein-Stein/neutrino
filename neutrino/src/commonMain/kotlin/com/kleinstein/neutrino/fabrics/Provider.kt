package com.kleinstein.neutrino.fabrics

class Provider<T: Any>(private val fabric: () -> T): IFabric<T> {
    override fun buildOrGet(): T = fabric()
}
