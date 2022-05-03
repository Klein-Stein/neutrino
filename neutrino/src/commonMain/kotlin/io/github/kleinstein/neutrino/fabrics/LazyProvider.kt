package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.ILazyFabric

class LazyProvider<T>(private val fabric: () -> T): ILazyFabric<T> {

    override fun buildOrGet(): Lazy<T> = lazy(fabric)
}
