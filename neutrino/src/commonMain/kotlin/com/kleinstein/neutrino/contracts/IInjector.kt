package com.kleinstein.neutrino.contracts

interface IInjector: INamed, IResolvable, IBuildable<IInjector>, IExtendable<IModule>,
    Collection<IModule> {
    fun containsModuleName(name: String): Boolean
}
