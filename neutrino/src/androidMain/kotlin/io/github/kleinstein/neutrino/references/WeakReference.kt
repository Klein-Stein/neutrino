package io.github.kleinstein.neutrino.references

import java.lang.ref.WeakReference as JvmWeakReference

actual typealias WeakReference<T> = JvmWeakReference<T>
