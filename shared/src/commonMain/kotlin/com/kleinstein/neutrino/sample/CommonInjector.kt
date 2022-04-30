package com.kleinstein.neutrino.sample

import com.kleinstein.neutrino.*

object CommonInjector {
    val mainModule = DI.module("main") {
        lazySingleton { Greeting() }
        provider { GreetingContainer(resolve()) }
    }

    val injector = DI.injector("main") {
        import(mainModule)
    }.build()
}