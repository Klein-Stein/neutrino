package io.github.kleinstein.neutrino.contracts

/**
 * Module interface
 *
 * Use this interface to implement a module with custom behaviour
 */
interface IModule: INamed, IResolvable, IBuildable<IModule>,
    Collection<Map.Entry<String, IFabric<*>>> {

    /**
     * Adds a new fabric to the module
     *
     * @param tag This tag will be used to find the dependency fabric in the module
     * @param fabric An instance of the [IFabric] implementation
     */
    fun addFabric(tag: String, fabric: IFabric<*>)

    /**
     * Removes a fabric from the module by tag
     *
     * @param tag Fabric tag
     * @return Boolean result
     */
    fun removeFabric(tag: String): Boolean

    /**
     * Checks if a module contains a fabric with the passed tag
     *
     * @param tag Fabric tag
     * @return Boolean result
     */
    fun containsTag(tag: String): Boolean
}
