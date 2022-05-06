package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.fabrics.Provider
import io.github.kleinstein.neutrino.contracts.*
import io.github.kleinstein.neutrino.fabrics.Singleton
import io.github.kleinstein.neutrino.fabrics.WeakSingleton

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
    addFabric(T::class.simpleName!!, fabric)

inline fun <reified T: Any> IModule.singleton(noinline fabric: () -> T) {
    this.addFabric(Singleton(fabric))
}

fun <T: Any> IModule.singleton(tag: String, fabric: () -> T) {
    this.addFabric(tag, Singleton(fabric))
}

inline fun <reified T: Any> IModule.weakSingleton(noinline fabric: () -> T) {
    this.addFabric(WeakSingleton(fabric))
}

fun <T: Any> IModule.weakSingleton(tag: String, fabric: () -> T) {
    this.addFabric(tag, WeakSingleton(fabric))
}

inline fun <reified T: Any> IModule.provider(noinline fabric: () -> T) {
    this.addFabric(Provider(fabric))
}

fun <T: Any> IModule.provider(tag: String, fabric: () -> T) {
    this.addFabric(tag, Provider(fabric))
}

inline fun <reified T: Any> IResolvable.resolve(tag: String): T = resolve(tag, T::class)

inline fun <reified T: Any> IResolvable.resolve(): T = resolve(T::class)

inline fun <reified T: Any> IResolvable.resolveLazy(tag: String): Lazy<T> =
    lazy{ resolve(tag, T::class) }

inline fun <reified T: Any> IResolvable.resolveLazy(): Lazy<T> = lazy { resolve(T::class) }
