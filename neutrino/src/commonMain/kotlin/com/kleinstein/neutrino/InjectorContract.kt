package com.kleinstein.neutrino

import kotlin.reflect.KClass

interface InjectorContract {
    fun <T : Any>register(tag: String? = null, clazz: KClass<T>, fabric: () -> T)
    fun <T : Any>register(clazz: KClass<T>, fabric: () -> T)
}