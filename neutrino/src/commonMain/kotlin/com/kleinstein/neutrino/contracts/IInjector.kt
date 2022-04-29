package com.kleinstein.neutrino.contracts

interface IInjector: IResolvable, IBuildable<IInjector> {
    fun import(module: IModule)
    fun importAll(vararg modules: IModule)
}