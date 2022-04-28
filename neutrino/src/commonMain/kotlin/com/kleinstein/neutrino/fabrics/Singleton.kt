package com.kleinstein.neutrino.fabrics

class Singleton<T>(fabric: () -> T): IFabric<T> {
    private val instance = fabric()

    override fun build(): T = instance
}
