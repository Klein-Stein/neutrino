package com.kleinstein.neutrino.fabrics

class Provider<T>(private val fabric: () -> T): IFabric<T> {
    override fun build(): T = fabric()
}
