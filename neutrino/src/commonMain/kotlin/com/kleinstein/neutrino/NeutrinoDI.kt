package com.kleinstein.neutrino

import com.kleinstein.neutrino.contracts.*
import com.kleinstein.neutrino.exceptions.NeutrinoException

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

    override operator fun get(name: String): IInjector = injectors[name]!!
    override operator fun invoke(): IInjector {
        if (injectors.size == 1) {
            injectors.entries.first().value
        }
        throw NeutrinoException(
            "More than 1 registered injector found, use `[]` to select the correct injector"
        )
    }

    override fun build(): NeutrinoDI {
        body()
        return this
    }
}
