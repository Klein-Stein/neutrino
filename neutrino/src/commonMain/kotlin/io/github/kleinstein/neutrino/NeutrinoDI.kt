package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.contracts.IModule
import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import kotlin.reflect.KType

/**
 * Implementation of the [DI container interface][DI]
 *
 * This class is used to hold all modules. Neutrino already has a global instance of
 * this DI container but you can still create additional instances.
 * @see DI.global
 *
 * @property body Initialization block of modules
 * @property size The number of attached modules
 * @constructor Creates a new DI container
 */
class NeutrinoDI(private val body: (DI.() -> Unit)? = null): DI {
    private val modules = mutableListOf<IModule>()

    override val size: Int
        get() = modules.size

    override fun attach(child: IModule) {
        modules.add(child.build())
    }

    override fun attachAll(vararg children: IModule) {
        modules.addAll(children)
        children.forEach { it.build() }
    }

    override fun contains(name: String): Boolean = modules.any { it.name == name }

    override fun contains(element: IModule): Boolean = modules.contains(element)

    override fun detach(name: String): IModule? {
        val module = modules.firstOrNull { it.name == name }
        if (module != null) {
            modules.remove(module)
        }
        return module
    }

    override fun containsAll(elements: Collection<IModule>): Boolean =
        modules.containsAll(elements)

    override fun isEmpty(): Boolean = modules.isEmpty()

    override fun iterator(): Iterator<IModule> = modules.iterator()

    override operator fun get(name: String): IModule = modules.firstOrNull { it.name == name } ?:
        throw NeutrinoException("Module `$name` not found")

    override fun <T : Any> resolve(kType: KType, tag: String?): T = resolve(Key(
        type = kType,
        tag = tag,
    ))

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> resolve(key: Key): T {
        val module = modules.firstOrNull { it.contains(key) }
        return module?.resolve(key) ?: throw NeutrinoException("The `$key` not found")
    }

    override fun build(): DI {
        body?.let { it() }
        return this
    }
}
