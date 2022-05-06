package io.github.kleinstein.neutrino.contracts

import kotlin.reflect.KClass

interface IResolvable {
    fun <T: Any> resolve(clazz: KClass<out T>): T
    fun <T: Any> resolve(tag: String, clazz: KClass<out T>): T
}
