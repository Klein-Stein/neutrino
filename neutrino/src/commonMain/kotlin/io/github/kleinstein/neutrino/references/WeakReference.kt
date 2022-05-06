package io.github.kleinstein.neutrino.references

expect class WeakReference<T: Any>(referred: T) {
    fun get(): T?
    fun clear()
}
