package com.kleinstein.neutrino.contracts

interface IExtendable<T> {
    fun import(child: T)
    fun importAll(vararg children: T)
}