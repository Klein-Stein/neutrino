package io.github.kleinstein.neutrino.references

import kotlin.native.ref.WeakReference as NativeWeakReference

actual typealias WeakReference<T> = NativeWeakReference<T>
