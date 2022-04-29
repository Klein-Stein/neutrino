package com.kleinstein.neutrino.fabrics

interface IFabric<T: Any> {
    fun buildOrGet(): T
}
