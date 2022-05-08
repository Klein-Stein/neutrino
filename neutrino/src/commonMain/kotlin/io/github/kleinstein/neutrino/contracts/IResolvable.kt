package io.github.kleinstein.neutrino.contracts

import kotlin.reflect.KClass
import io.github.kleinstein.neutrino.exceptions.NeutrinoException

/**
 * Use this interface to mark a container as resolvable. Such containers allow to find children by
 * tags and class types
 */
interface IResolvable {

    /**
     * Returns a child from the container by its class type
     *
     * @param T Child type
     * @param clazz Child type
     * @return Resolved child or throws [an exception][NeutrinoException]
     */
    fun <T: Any> resolve(clazz: KClass<out T>): T

    /**
     * Returns a child from the container by passed tag and child class
     *
     * @param T Child type
     * @param tag Child tag ([fabric][IFabric] tag)
     * @param clazz Child type
     * @return Resolved child or throws [an exception][NeutrinoException]
     */
    fun <T: Any> resolve(tag: String, clazz: KClass<out T>): T
}
