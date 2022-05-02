package com.kleinstein.neutrino.contracts

interface IExtendable<T: INamed> {
    fun attach(child: T)
    fun attachAll(vararg children: T)
    fun contains(name: String): Boolean
    fun detach(name: String): T?
}
