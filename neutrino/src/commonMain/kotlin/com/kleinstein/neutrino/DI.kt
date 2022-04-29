package com.kleinstein.neutrino

import com.kleinstein.neutrino.contracts.IBuildable
import com.kleinstein.neutrino.contracts.IExtendable
import com.kleinstein.neutrino.contracts.IInjector
import com.kleinstein.neutrino.contracts.IModule
import com.kleinstein.neutrino.exceptions.NeutrinoException

class DI(private val body: DI.() -> Unit): IBuildable<DI>, IExtendable<IInjector> {
    private val injectors = hashMapOf<String, IInjector>()

    override fun import(child: IInjector) {
        injectors[child.name] = child.build()
    }

    override fun importAll(vararg children: IInjector) {
        if (children.any()) {
            children.forEach { import(it) }
        }
    }

    operator fun get(name: String): IInjector = injectors[name]!!
    operator fun invoke(): IInjector {
        if (injectors.size == 1) {
            injectors.entries.first().value
        }
        throw NeutrinoException(
            "More than 1 registered injector found, use `[]` to select the correct injector"
        )
    }

    override fun build(): DI {
        body()
        return this
    }

    companion object {
        val global = DI {}
        fun module(name: String, body: IModule.() -> Unit): IModule = Module(name, body)
        fun injector(name: String, body: IExtendable<IModule>.() -> Unit): IInjector =
            Injector(name, body)
    }
}