package io.github.kleinstein.neutrino.references

/**
 * Weak reference allow not to prevent a referred object to be deleted by GC
 *
 * @param T Referred object type
 * @param referred Referred object
 */
expect class Weak<T: Any>(referred: T) {

    /**
     * Returns a referred object or `null` if the object was deleted by GC
     *
     * @return Referred object or `null`
     */
    fun get(): T?

    /**
     * Clears a reference to the object, next calls of [get] will return `null`
     */
    fun clear()
}
