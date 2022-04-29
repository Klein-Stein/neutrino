package com.kleinstein.neutrino.contracts

interface IBuildable<T> {
    fun build(): T
}