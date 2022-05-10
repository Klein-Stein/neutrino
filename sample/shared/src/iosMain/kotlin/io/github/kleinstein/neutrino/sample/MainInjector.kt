package io.github.kleinstein.neutrino.sample

import io.github.kleinstein.neutrino.DI
import io.github.kleinstein.neutrino.resolveLazy

class MainInjector {
    private val di = DI.global.apply {
        attach(CommonInjector.mainModule)
    }

    val greeting by di.resolveLazy<Greeting>()
}
