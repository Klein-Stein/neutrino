package io.github.kleinstein.neutrino.references

import kotlin.native.ref.WeakReference as NativeWeakReference

/**
 * iOS implementation of [weak references][WeakReference]
 *
 * @param T Referred object type
 */
actual typealias WeakReference<T> = NativeWeakReference<T>
