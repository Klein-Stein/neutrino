package com.kleinstein.neutrino

import com.kleinstein.neutrino.fabrics.Provider
import com.kleinstein.neutrino.fabrics.Singleton
import kotlin.reflect.KClass

class Injector: InjectorContract {
    override fun <T : Any> register(tag: String?, clazz: KClass<T>, fabric: () -> T) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> register(clazz: KClass<T>, fabric: () -> T) {
        this.register(clazz = clazz, fabric = fabric)
    }
}

fun <T>singleton(fabric: () -> T): Singleton<T> = Singleton(fabric)

fun <T>provider(fabric: () -> T): Provider<T> = Provider(fabric)
