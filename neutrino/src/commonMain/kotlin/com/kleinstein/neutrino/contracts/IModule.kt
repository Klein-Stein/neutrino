package com.kleinstein.neutrino.contracts

interface IModule: INamed, IResolvable, IBuildable<IModule>,
    Collection<Map.Entry<String, IFabric<*>>> {
    fun <T: Any> addFabric(tag: String, fabric: IFabric<T>)
    fun removeFabric(tag: String): Boolean
    fun containsTag(tag: String): Boolean
}
