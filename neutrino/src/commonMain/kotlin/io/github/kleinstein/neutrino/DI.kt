package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.fabrics.Provider
import io.github.kleinstein.neutrino.contracts.*
import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import io.github.kleinstein.neutrino.fabrics.Singleton
import io.github.kleinstein.neutrino.fabrics.WeakSingleton

/**
 * DI container interface
 */
interface DI: IBuildable<DI>, IExtendable<IInjector>, Collection<Map.Entry<String, IInjector>> {

    /**
     * Returns an attached injector by name
     *
     * @param name Injector name
     * @return An attached [IInjector]'s instance or throws [an exception][NeutrinoException]
     */
    operator fun get(name: String): IInjector

    /**
     * Returns an attached injector by name or the first one
     *
     * @param name Injector name (can be `null` if there is a single attached injector)
     * @return An attached [IInjector]'s instance or throws [an exception][NeutrinoException]
     */
    operator fun invoke(name: String? = null): IInjector

    companion object {
        /**
         * A global instance of [DI container][DI], use this instance to attach injectors
         */
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
