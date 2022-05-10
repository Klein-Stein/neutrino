package io.github.kleinstein.neutrino.references

import kotlin.native.ref.WeakReference as NativeWeakReference

/**
 * iOS implementation of [weak references][Weak]
 *
 * @param T Referred object type
 */
actual typealias Weak<T> = NativeWeakReference<T>
