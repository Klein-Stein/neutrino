package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.contracts.IExtendable
import io.github.kleinstein.neutrino.contracts.IInjector
import io.github.kleinstein.neutrino.contracts.IModule
import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import kotlin.reflect.KClass

class Injector(override val name: String, private val body: IExtendable<IModule>.() -> Unit) :
    IInjector {
    private val modules = mutableListOf<IModule>()

    override val size: Int
        get() = modules.size

    override fun attach(child: IModule) {
        modules.add(child.build())
    }

    override fun attachAll(vararg children: IModule) {
        if (children.any()) {
            modules.addAll(children)
            children.forEach { it.build() }
        }
    }

    override fun contains(name: String): Boolean = modules.any { it.name == name }

    override fun contains(element: IModule): Boolean = modules.contains(element)

    override fun detach(name: String): IModule? {
        val module = modules.find { it.name == name }
        if (module != null) {
            modules.remove(module)
        }
        return module
    }

    override fun containsAll(elements: Collection<IModule>): Boolean = modules.containsAll(elements)

    override fun isEmpty(): Boolean = modules.isEmpty()

    override fun iterator(): Iterator<IModule> = modules.iterator()

    override fun <T : Any> resolve(clazz: KClass<out T>): T =
        this.resolve(clazz.simpleName!!, clazz)

    override fun <T : Any> resolve(tag: String, clazz: KClass<out T>): T {
        modules.forEach { module ->
            if (module.containsTag(tag)) {
                return module.resolve(tag, clazz)
            }
        }
        throw NeutrinoException("The `$tag` not found")
    }

    override fun build(): IInjector {
        body()
        return this
    }
}
