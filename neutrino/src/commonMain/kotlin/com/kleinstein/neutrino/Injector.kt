package com.kleinstein.neutrino

import com.kleinstein.neutrino.contracts.IExtendable
import com.kleinstein.neutrino.contracts.IInjector
import com.kleinstein.neutrino.contracts.IModule
import com.kleinstein.neutrino.exceptions.NeutrinoException
import kotlin.reflect.KClass

class Injector(override val name: String, private val body: IExtendable<IModule>.() -> Unit) :
    IInjector {
    private val modules = mutableListOf<IModule>()

    override fun import(child: IModule) {
        modules.add(child.build())
    }

    override fun importAll(vararg children: IModule) {
        if (children.any()) {
            modules.addAll(children)
            children.forEach { it.build() }
        }
    }

    override fun <T : Any> resolve(clazz: KClass<out T>): T =
        this.resolve(clazz.simpleName!!, clazz)

    override fun <T : Any> resolve(tag: String, clazz: KClass<out T>): T {
        modules.forEach { module ->
            if (module.contains(tag)) {
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
