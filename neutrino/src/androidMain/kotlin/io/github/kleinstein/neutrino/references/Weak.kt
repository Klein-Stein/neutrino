package io.github.kleinstein.neutrino.references

import java.lang.ref.WeakReference as JvmWeakReference

/**
 * Android implementation of [weak references][Weak]
 *
 * @param T Referred object type
 */
actual typealias Weak<T> = JvmWeakReference<T>
