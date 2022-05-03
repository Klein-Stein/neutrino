package io.github.kleinstein.neutrino.contracts

interface IInjector: INamed, IResolvable, IBuildable<IInjector>, IExtendable<IModule>,
    Collection<IModule>
