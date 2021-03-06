package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.contracts.IModule
import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import io.github.kleinstein.neutrino.contracts.IFabric
import kotlin.reflect.KType

/**
 * [DI] container's module
 *
 * Modules allow to split dependencies into separate independent groups that can be convenient for
 * decomposition of their initialization
 *
 * @property name Module name
 * @param body Initialization block of dependencies (optional)
 * @property size The number of added injections
 * @constructor Creates a new module
 */
class Module(override val name: String, private val body: (IModule.() -> Unit)? = null) : IModule {
    private val fabrics = hashMapOf<Key, IFabric<*>>()

    override val size: Int
        get() = fabrics.size

    override fun addFabric(key: Key, fabric: IFabric<*>) {
        fabrics[key] = fabric
    }

    override fun removeFabric(key: Key): Boolean = fabrics.remove(key) != null

    override fun build(): IModule {
        body?.let { it() }
        return this
    }

    override fun containsAll(elements: Collection<Map.Entry<Key, IFabric<*>>>): Boolean =
        fabrics.entries.containsAll(elements)

    override fun isEmpty(): Boolean = fabrics.isEmpty()

    override fun iterator(): Iterator<Map.Entry<Key, IFabric<*>>> = fabrics.iterator()

    override fun contains(key: Key): Boolean = fabrics.containsKey(key)

    override fun contains(element: Map.Entry<Key, IFabric<*>>): Boolean =
        fabrics.entries.contains(element)

    override fun <T : Any> resolve(kType: KType, tag: String?): T = resolve(Key(
        type = kType,
        tag = tag,
    ))

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> resolve(key: Key): T {
        val fabric = fabrics[key] ?: throw NeutrinoException("The `$key` not found")
        val obj = fabric.buildOrGet()
        return obj as? T ?: throw NeutrinoException(
            "Resolved object can't be casted to required type"
        )
    }

    override fun <T : Any> resolveLazy(key: Key): Lazy<T> = lazy { resolve(key) }

    override fun <T : Any> resolveLazy(kType: KType, tag: String?): Lazy<T> = lazy {
        resolve(kType, tag)
    }
}
