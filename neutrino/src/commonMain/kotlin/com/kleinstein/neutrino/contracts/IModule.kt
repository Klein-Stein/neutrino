package com.kleinstein.neutrino.contracts

interface IModule: INamed, IResolvable, IBuildable<IModule> {
    fun <T: Any> addFabric(tag: String, fabric: IFabric<T>)
    fun contains(tag: String): Boolean
}
