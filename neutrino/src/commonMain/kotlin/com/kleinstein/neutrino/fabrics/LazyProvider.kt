package com.kleinstein.neutrino.fabrics

import com.kleinstein.neutrino.contracts.ILazyFabric

class LazyProvider<T>(private val fabric: () -> T): ILazyFabric<T> {

    override fun buildOrGet(): Lazy<T> = lazy(fabric)
}
