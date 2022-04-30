package com.kleinstein.neutrino.fabrics

import com.kleinstein.neutrino.contracts.IFabric

class Singleton<T: Any>(fabric: () -> T): IFabric<T> {
    private val instance = fabric()

    override fun buildOrGet(): T = instance
}
