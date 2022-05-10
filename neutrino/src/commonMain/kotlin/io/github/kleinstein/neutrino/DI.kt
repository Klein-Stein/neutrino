package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.fabrics.Provider
import io.github.kleinstein.neutrino.contracts.*
import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import io.github.kleinstein.neutrino.fabrics.Singleton
import io.github.kleinstein.neutrino.references.Weak
import kotlin.native.concurrent.ThreadLocal
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

    @ThreadLocal
    companion object {
        /**
         * A global instance of [DI container][DI], use this instance to attach injectors
         */
        val global: DI = NeutrinoDI {}

        /**
         * Creates a new module without calling the [IModule.build] method
         *
         * @param name Module name
         * @param body Initialization block of dependencies (optional)
         * @return New module
         */
        fun module(name: String, body: (IModule.() -> Unit)? = null): IModule = Module(name, body)
    }
}

/**
 * Adds a new fabric to the module
 *
 * @param fabric An instance of the [IFabric] implementation
 */
inline fun <reified T: Any> IModule.addFabric(fabric: IFabric<T>) =
    addFabric(Key(type = typeOf<T>()), fabric)

/**
 * Adds a fabric to the [module][IModule] as the singleton fabric
 *
 * @param fabric Dependency fabric
 */
inline fun <reified T: Any> IModule.singleton(noinline fabric: () -> T) {
    this.addFabric(Singleton(fabric))
}

/**
 * Adds a fabric to the [module][IModule] as the singleton fabric
 *
 * @param tag Fabric tag
 * @param fabric Dependency fabric
 */
inline fun <reified T: Any> IModule.singleton(tag: String, noinline fabric: () -> T) {
    this.addFabric(Key(
        type = typeOf<T>(),
        tag = tag,
    ), Singleton(fabric))
}

/**
 * Adds a fabric to the [module][IModule] as the weak singleton fabric
 *
 * @param fabric Dependency fabric
 */
inline fun <reified T: Any> IModule.weakSingleton(noinline fabric: () -> T) {
    this.addFabric(Singleton<Weak<T>> {
        Weak(fabric())
    })
}

/**
 * Adds a fabric to the [module][IModule] as the weak singleton fabric
 *
 * @param tag Fabric tag
 * @param fabric Dependency fabric
 */
inline fun <reified T: Any> IModule.weakSingleton(tag: String, noinline fabric: () -> T) {
    this.addFabric(Key(
        type = typeOf<Weak<T>>(),
        tag = tag,
    ), Singleton {
        Weak(fabric())
    })
}

/**
 * Adds a fabric to the [module][IModule] as the provider fabric
 *
 * @param fabric Dependency fabric
 */
inline fun <reified T: Any> IModule.provider(noinline fabric: () -> T) {
    this.addFabric(Provider(fabric))
}

/**
 * Adds a fabric to the [module][IModule] as the provider fabric
 *
 * @param tag Fabric tag
 * @param fabric Dependency fabric
 */
inline fun <reified T: Any> IModule.provider(tag: String, noinline fabric: () -> T) {
    this.addFabric(Key(
        type = typeOf<T>(),
        tag = tag,
    ), Provider(fabric))
}

/**
 * Returns a child from the container by the passed tag
 *
 * @param T Child type
 * @param tag Child tag
 * @return Resolved child or throws [an exception][NeutrinoException]
 */
inline fun <reified T: Any> IResolvable.resolve(tag: String): T = resolve(typeOf<T>(), tag)

/**
 * Returns a child from the container
 *
 * @param T Child type
 * @return Resolved child or throws [an exception][NeutrinoException]
 */
inline fun <reified T: Any> IResolvable.resolve(): T = resolve(typeOf<T>())

/**
 * Lazy returns a child from the container by the passed tag
 *
 * @param T Child type
 * @param tag Child tag
 * @return Lazy resolved child or throws [an exception][NeutrinoException]
 */
inline fun <reified T: Any> IResolvable.resolveLazy(tag: String): Lazy<T> =
    resolveLazy(typeOf<T>(), tag)

/**
 * Lazy returns a child from the container by the passed tag
 *
 * @param T Child type
 * @return Lazy resolved child or throws [an exception][NeutrinoException]
 */
inline fun <reified T: Any> IResolvable.resolveLazy(): Lazy<T> = resolveLazy(typeOf<T>())
