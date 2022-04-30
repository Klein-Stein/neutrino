package com.kleinstein.neutrino.contracts

import kotlin.reflect.KClass

interface IResolvable {
    fun <T: Any> resolve(clazz: KClass<out T>): T
    fun <T: Any> resolve(tag: String, clazz: KClass<out T>): T
    fun <T: Any> resolveLazy(clazz: KClass<out T>): Lazy<T>
    fun <T: Any> resolveLazy(tag: String, clazz: KClass<out T>): Lazy<T>
}
