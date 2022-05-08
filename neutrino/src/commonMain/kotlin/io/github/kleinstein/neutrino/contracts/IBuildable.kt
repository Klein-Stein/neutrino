package io.github.kleinstein.neutrino.contracts

/**
 * Use this interface to mark a class as buildable, the class must implement [build] method
 *
 * @param T Implementation type or an interface to represent this implementation
 */
interface IBuildable<T> {

    /**
     * Builds an [IBuildable] implementation
     *
     * @return itself or an interface to represent itself
     */
    fun build(): T
}