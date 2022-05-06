package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.IFabric
import io.github.kleinstein.neutrino.references.WeakReference

class WeakSingleton<T: Any>(private val fabric: () -> T): IFabric<WeakReference<T>> {
    private lateinit var ref: WeakReference<T>

    override fun buildOrGet(): WeakReference<T> {
        if (!this::ref.isInitialized) {
            this.ref = WeakReference(this.fabric())
        }
        return this.ref
    }
}
