package io.github.kleinstein.neutrino.references

import java.lang.ref.WeakReference as JvmWeakReference

/**
 * Android implementation of [weak references][WeakReference]
 *
 * @param T Referred object type
 */
actual typealias WeakReference<T> = JvmWeakReference<T>
