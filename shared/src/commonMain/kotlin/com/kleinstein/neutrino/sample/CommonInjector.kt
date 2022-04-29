package com.kleinstein.neutrino.sample

import com.kleinstein.neutrino.*
import com.kleinstein.neutrino.contracts.lazySingleton
import com.kleinstein.neutrino.contracts.provider
import com.kleinstein.neutrino.contracts.resolve

object CommonInjector {
    val mainModule = Module("main") {
        lazySingleton { Greeting() }
        provider { GreetingContainer(resolve()) }
    }

    val injector = Injector {
        import(mainModule)
    }.build()
}