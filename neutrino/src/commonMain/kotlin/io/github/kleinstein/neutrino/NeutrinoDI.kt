package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.contracts.IInjector
import io.github.kleinstein.neutrino.exceptions.NeutrinoException

/**
 * Implementation of [the DI container interface][DI]
 *
 * This class is used to hold all dependency injectors. Neutrino already has a global instance of
 * this DI container but you can still create additional instances.
 * @see DI.global
 *
 * @property body Initialization block of injectors
 * @property size The number of attached injectors
 * @constructor Creates a new DI container
 */
class NeutrinoDI(private val body: NeutrinoDI.() -> Unit): DI {
    private val injectors = hashMapOf<String, IInjector>()

    override val size: Int
        get() = injectors.size

    override fun attach(child: IInjector) {
        injectors[child.name] = child.build()
    }

    override fun attachAll(vararg children: IInjector) {
        if (children.any()) {
            children.forEach { attach(it) }
        }
    }

    override fun contains(name: String): Boolean = injectors.containsKey(name)

    override fun contains(element: Map.Entry<String, IInjector>): Boolean =
        injectors.entries.contains(element)

    override fun detach(name: String): IInjector? = injectors.remove(name)

    override fun containsAll(elements: Collection<Map.Entry<String, IInjector>>): Boolean =
        injectors.entries.containsAll(elements)

    override fun isEmpty(): Boolean = injectors.isEmpty()

    override fun iterator(): Iterator<Map.Entry<String, IInjector>> = injectors.iterator()

    override operator fun get(name: String): IInjector = injectors[name] ?: throw NeutrinoException(
        "Injector `$name` not found"
    )

    override operator fun invoke(name: String?): IInjector {
        if (name == null && injectors.size == 1) {
            return injectors.entries.first().value
        }
        if (name != null) {
            return get(name)
        }
        throw NeutrinoException(
            "More than 1 registered injector found, pass a name to select the correct injector"
        )
    }

    override fun build(): NeutrinoDI {
        body()
        return this
    }
}
