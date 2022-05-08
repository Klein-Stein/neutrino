package io.github.kleinstein.neutrino.sample.android

import android.app.Application
import io.github.kleinstein.neutrino.sample.CommonInjector
import io.github.kleinstein.neutrino.sample.Greeting
import io.github.kleinstein.neutrino.DI
import io.github.kleinstein.neutrino.provider
import io.github.kleinstein.neutrino.singleton

class App: Application() {
    private val androidModule = DI.module("android") {
        singleton { Greeting() }
        provider { Greeting() }
        singleton("singleton") { Greeting() }
        provider("provider") { Greeting() }
    }

    private val injector = DI.injector("main") {
        attachAll(androidModule, CommonInjector.mainModule)
    }

    init {
        DI.global.attach(injector)
    }
}