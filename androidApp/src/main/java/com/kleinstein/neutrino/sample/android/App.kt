package com.kleinstein.neutrino.sample.android

import android.app.Application
import com.kleinstein.neutrino.*
import com.kleinstein.neutrino.sample.CommonInjector
import com.kleinstein.neutrino.sample.Greeting

class App: Application() {
    private val androidModule = DI.module("android") {
        singleton { Greeting() }
        lazySingleton { Greeting() }
        provider { Greeting() }
        singleton("singleton") { Greeting() }
        lazySingleton("lazySingleton") { Greeting() }
        provider("provider") { Greeting() }
    }

    private val injector = DI.injector("main") {
        importAll(androidModule, CommonInjector.mainModule)
    }

    init {
        DI.global.import(injector)
    }
}