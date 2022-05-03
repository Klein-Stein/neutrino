package io.github.kleinstein.neutrino.sample

import io.github.kleinstein.neutrino.DI
import io.github.kleinstein.neutrino.resolve

class MainInjector {
    private val injector = DI.injector("main") {
        attach(CommonInjector.mainModule)
    }.build()

    val greeting = injector.resolve<Greeting>()
}
