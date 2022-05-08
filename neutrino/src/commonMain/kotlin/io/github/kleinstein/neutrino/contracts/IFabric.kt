package io.github.kleinstein.neutrino.contracts

/**
 * Use this interface to implement a fabric with custom behaviour
 *
 * @param T Injectable dependency type
 */
interface IFabric<T: Any> {

    /**
     * Returns a dependency for the injector, if the dependency is not initialized it will be
     * created first
     *
     * @return A dependency for the injector
     */
    fun buildOrGet(): T
}
