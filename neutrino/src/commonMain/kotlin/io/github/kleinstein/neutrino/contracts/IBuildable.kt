package io.github.kleinstein.neutrino.contracts

interface IBuildable<T> {
    fun build(): T
}