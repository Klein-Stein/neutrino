package io.github.kleinstein.neutrino.fabrics

import io.github.kleinstein.neutrino.contracts.IFabric
import io.github.kleinstein.neutrino.references.WeakReference

/**
 * [Fabric][IFabric] that implements DI singleton pattern by [weak references][WeakReference]
 *
 * The weak singletons use the [fabric] once to create a new weak reference on the injectable
 * dependency when [buildOrGet] is called, all next times they will return the earlier created
 * reference. The weak singleton is not a dependency's owner, if the dependency will be deleted by
 * GC, the weak singleton will return a weak reference on `null`
 *
 * @param T Injectable dependency type
 * @param fabric Initialization block of the injectable dependency
 * @constructor Creates a new weak singleton
 */
class WeakSingleton<T: Any>(private val fabric: () -> T): IFabric<WeakReference<T>> {
    private lateinit var ref: WeakReference<T>

    override fun buildOrGet(): WeakReference<T> {
        if (!this::ref.isInitialized) {
            this.ref = WeakReference(this.fabric())
        }
        return this.ref
    }
}
