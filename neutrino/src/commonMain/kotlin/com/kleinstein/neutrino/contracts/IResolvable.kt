package com.kleinstein.neutrino.contracts

import kotlin.reflect.KClass

interface IResolvable {
    fun <T: Any> resolve(clazz: KClass<out T>): T
    fun <T: Any> resolve(tag: String, clazz: KClass<out T>): T
}

inline fun <reified T: Any> IResolvable.resolve(tag: String): T = resolve(tag, T::class)

inline fun <reified T: Any> IResolvable.resolve(): T = resolve(T::class)
