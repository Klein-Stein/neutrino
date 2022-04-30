package com.kleinstein.neutrino.fabrics

import com.kleinstein.neutrino.contracts.IFabric

class Provider<T: Any>(private val fabric: () -> T): IFabric<T> {
    override fun buildOrGet(): T = fabric()
}
