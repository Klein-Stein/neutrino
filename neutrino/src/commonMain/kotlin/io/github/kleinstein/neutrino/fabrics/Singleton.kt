package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.IFabric

class Singleton<T: Any>(private val fabric: () -> T): IFabric<T> {
    private lateinit var instance: T

    override fun buildOrGet(): T {
        if (!this::instance.isInitialized) {
            this.instance = this.fabric()
        }
        return this.instance
    }
}
