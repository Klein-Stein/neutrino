package io.github.kleinstein.neutrino.contracts

/**
 * Injector interface
 *
 * Use this interface to implement an injector with custom behavior
 */
interface IInjector: INamed, IResolvable, IBuildable<IInjector>, IExtendable<IModule>,
    Collection<IModule>
