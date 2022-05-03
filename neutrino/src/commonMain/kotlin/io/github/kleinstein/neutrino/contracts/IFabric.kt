package io.github.kleinstein.neutrino.contracts

interface IFabric<T: Any> {
    fun buildOrGet(): T
}
