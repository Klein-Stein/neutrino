package io.github.kleinstein.neutrino.contracts

import io.github.kleinstein.neutrino.Key
import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import kotlin.reflect.KType

/**
 * Use this interface to mark a container as resolvable. Such containers allow to find children by
 * tags and class types
 */
interface IResolvable {

    /**
     * Returns a child from the container by the passed key
     *
     * @param T Child type
     * @param key Child key
     * @return Resolved child or throws [an exception][NeutrinoException]
     */
    fun <T: Any> resolve(key: Key): T

    /**
     * Returns a child from the container by passed tag and child class
     *
     * @param T Child type
     * @param kType Child type
     * @param tag Child tag (optional)
     * @return Resolved child or throws [an exception][NeutrinoException]
     */
    fun <T: Any> resolve(kType: KType, tag: String? = null): T
}
