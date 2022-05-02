package com.kleinstein.neutrino.sample

import com.kleinstein.neutrino.DI
import com.kleinstein.neutrino.resolve

class MainInjector {
    private val injector = DI.injector("main") {
        attach(CommonInjector.mainModule)
    }.build()

    val greeting = injector.resolve<Greeting>()
}
