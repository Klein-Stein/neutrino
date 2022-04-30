package com.kleinstein.neutrino

import com.kleinstein.neutrino.contracts.*
import com.kleinstein.neutrino.fabrics.LazySingleton
import com.kleinstein.neutrino.fabrics.Provider
import com.kleinstein.neutrino.fabrics.Singleton

interface DI: IBuildable<DI>, IExtendable<IInjector>, Collection<Map.Entry<String, IInjector>> {
    operator fun get(name: String): IInjector
    operator fun invoke(): IInjector

    companion object {
        val global = NeutrinoDI {}
        fun module(name: String, body: IModule.() -> Unit): IModule = Module(name, body)
        fun injector(name: String, body: IExtendable<IModule>.() -> Unit): IInjector =
            Injector(name, body)
    }
}

inline fun <reified T: Any> IModule.addFabric(fabric: IFabric<T>) =
    addFabric(T::class.qualifiedName!!, fabric)

inline fun <reified T: Any> IModule.singleton(noinline fabric: () -> T) {
    this.addFabric(Singleton(fabric))
}

fun <T: Any> IModule.singleton(tag: String, fabric: () -> T) {
    this.addFabric(tag, Singleton(fabric))
}

inline fun <reified T : Any> IModule.lazySingleton(noinline fabric: () -> T) {
    this.addFabric(LazySingleton(fabric))
}

fun <T: Any> IModule.lazySingleton(tag: String, fabric: () -> T) {
    this.addFabric(tag, LazySingleton(fabric))
}

inline fun <reified T: Any> IModule.provider(noinline fabric: () -> T) {
    this.addFabric(Provider(fabric))
}

fun <T: Any> IModule.provider(tag: String, fabric: () -> T) {
    this.addFabric(tag, Provider(fabric))
}

inline fun <reified T: Any> IResolvable.resolve(tag: String): T = resolve(tag, T::class)

inline fun <reified T: Any> IResolvable.resolve(): T = resolve(T::class)
