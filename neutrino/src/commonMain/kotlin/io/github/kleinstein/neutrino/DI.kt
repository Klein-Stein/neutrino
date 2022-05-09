package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.fabrics.Provider
import io.github.kleinstein.neutrino.contracts.*
import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import io.github.kleinstein.neutrino.fabrics.Singleton
import io.github.kleinstein.neutrino.fabrics.WeakSingleton
import kotlin.reflect.typeOf

/**
 * DI container interface
 */
interface DI: IResolvable, IBuildable<DI>, Collection<IModule> {

    /**
     * Attaches a new module to the DI container
     *
     * @param child [IModule] instance
     */
    fun attach(child: IModule)

    /**
     * Attaches a collection of modules to the DI container
     *
     * @param children A collection of children
     */
    fun attachAll(vararg children: IModule)

    /**
     * Checks if the DI container contains a module with the passed name
     *
     * @param name Module name
     * @return Boolean result
     */
    fun contains(name: String): Boolean

    /**
     * Detaches an existing module from the DI container
     *
     * @param name Module name
     * @return Module or `null` if the module doesn't exist
     */
    fun detach(name: String): IModule?

    /**
     * Returns an attached module by name
     *
     * @param name Module name
     * @return An attached [IModule]'s instance or throws [an exception][NeutrinoException]
     */
    operator fun get(name: String): IModule

    companion object {
        /**
         * A global instance of [DI container][DI], use this instance to attach injectors
         */
        val global = NeutrinoDI {}
        fun module(name: String, body: IModule.() -> Unit): IModule = Module(name, body)
    }
}

inline fun <reified T: Any> IModule.addFabric(fabric: IFabric<T>) =
    addFabric(Key(type = typeOf<T>()), fabric)

inline fun <reified T: Any> IModule.singleton(noinline fabric: () -> T) {
    this.addFabric(Singleton(fabric))
}

inline fun <reified T: Any> IModule.singleton(tag: String, noinline fabric: () -> T) {
    this.addFabric(Key(
        type = typeOf<T>(),
        tag = tag,
    ), Singleton(fabric))
}

inline fun <reified T: Any> IModule.weakSingleton(noinline fabric: () -> T) {
    this.addFabric(WeakSingleton(fabric))
}

inline fun <reified T: Any> IModule.weakSingleton(tag: String, noinline fabric: () -> T) {
    this.addFabric(Key(
        type = typeOf<T>(),
        tag = tag,
    ), WeakSingleton(fabric))
}

inline fun <reified T: Any> IModule.provider(noinline fabric: () -> T) {
    this.addFabric(Provider(fabric))
}

inline fun <reified T: Any> IModule.provider(tag: String, noinline fabric: () -> T) {
    this.addFabric(Key(
        type = typeOf<T>(),
        tag = tag,
    ), Provider(fabric))
}

inline fun <reified T: Any> IResolvable.resolve(tag: String): T = resolve(typeOf<T>(), tag)

inline fun <reified T: Any> IResolvable.resolve(): T = resolve(typeOf<T>())

inline fun <reified T: Any> IResolvable.resolveLazy(tag: String): Lazy<T> =
    lazy{ resolve(typeOf<T>(), tag) }

inline fun <reified T: Any> IResolvable.resolveLazy(): Lazy<T> = lazy { resolve(typeOf<T>()) }
