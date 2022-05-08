package io.github.kleinstein.neutrino.contracts

/**
 * Use this interface to mark a container class as extendable
 *
 * @param T A child type that implements [INamed] interface
 */
interface IExtendable<T: INamed> {

    /**
     * Attaches a new child to the container
     *
     * @param child Child instance
     */
    fun attach(child: T)

    /**
     * Attaches a collection of children to the container
     *
     * @param children A collection of children
     */
    fun attachAll(vararg children: T)

    /**
     * Checks if the container contains a child with the passed name
     *
     * @param name Child name
     * @return Boolean result
     */
    fun contains(name: String): Boolean

    /**
     * Detaches an existing child from the container
     *
     * @param name Child name
     * @return Child or `null` if the child doesn't exist
     */
    fun detach(name: String): T?
}
