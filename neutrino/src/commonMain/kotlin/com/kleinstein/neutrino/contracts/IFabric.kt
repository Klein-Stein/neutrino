package com.kleinstein.neutrino.contracts

interface IFabric<T: Any> {
    fun buildOrGet(): T
}
