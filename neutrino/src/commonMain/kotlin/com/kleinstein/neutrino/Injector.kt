package com.kleinstein.neutrino

import com.kleinstein.neutrino.contracts.IInjector
import com.kleinstein.neutrino.contracts.IModule
import com.kleinstein.neutrino.exceptions.NeutrinoException
import kotlin.reflect.KClass

class Injector(private val body: IInjector.() -> Unit): IInjector {
    private val modules = mutableListOf<IModule>()

    override fun import(module: IModule) {
        modules.add(module.build())
    }

    override fun importAll(vararg modules: IModule) {
        if (modules.any()) {
            this.modules.addAll(modules)
            modules.forEach { it.build() }
        }
    }

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
