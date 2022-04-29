package com.kleinstein.neutrino.contracts

import com.kleinstein.neutrino.fabrics.IFabric
import com.kleinstein.neutrino.fabrics.LazySingleton
import com.kleinstein.neutrino.fabrics.Provider
import com.kleinstein.neutrino.fabrics.Singleton

interface IModule: IResolvable, IBuildable<IModule> {
    val name: String

    fun <T: Any> addFabric(tag: String, fabric: IFabric<T>)
    fun containsTag(tag: String): Boolean
}

inline fun <reified T: Any> IModule.addFabric(fabric: IFabric<T>) =
    addFabric(T::class.qualifiedName!!, fabric)

inline fun <reified T: Any> IModule.singleton(noinline fabric: () -> T) {
    this.addFabric(Singleton(fabric))
}

fun <T: Any> IModule.singleton(tag: String, fabric: () -> T) {
    this.addFabric(tag, Singleton(fabric))
}

inline fun <reified T : Any> IModule.lazySingleton(noinline fabric: () -> T) {
    this.addFabric(LazySingleton(fabric))
}

fun <T: Any> IModule.lazySingleton(tag: String, fabric: () -> T) {
    this.addFabric(tag, LazySingleton(fabric))
}

inline fun <reified T: Any> IModule.provider(noinline fabric: () -> T) {
    this.addFabric(Provider(fabric))
}

fun <T: Any> IModule.provider(tag: String, fabric: () -> T) {
    this.addFabric(tag, Provider(fabric))
}
