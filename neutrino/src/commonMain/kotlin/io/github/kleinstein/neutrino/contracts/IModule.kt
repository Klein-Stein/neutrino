package io.github.kleinstein.neutrino.contracts

import io.github.kleinstein.neutrino.Key

/**
 * Module interface
 *
 * Use this interface to implement a module with custom behaviour
 */
interface IModule: INamed, IResolvable, IBuildable<IModule>,
    Collection<Map.Entry<Key, IFabric<*>>> {

    /**
     * Adds a new fabric to the module
     *
     * @param key Fabric key
     * @param fabric An instance of the [IFabric] implementation
     */
    fun addFabric(key: Key, fabric: IFabric<*>)

    /**
     * Removes a fabric from the module by key
     *
     * @param key Fabric key
     * @return Boolean result
     */
    fun removeFabric(key: Key): Boolean

    /**
     * Checks if a module contains a fabric with the passed key
     *
     * @param key Fabric key
     * @return Boolean result
     */
    fun contains(key: Key): Boolean
}
