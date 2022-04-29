package com.kleinstein.neutrino.sample.android

import android.app.Application
import com.kleinstein.neutrino.*
import com.kleinstein.neutrino.contracts.lazySingleton
import com.kleinstein.neutrino.contracts.provider
import com.kleinstein.neutrino.contracts.singleton
import com.kleinstein.neutrino.sample.CommonInjector
import com.kleinstein.neutrino.sample.Greeting

class App: Application() {
    private val androidModule = Module("android") {
        singleton { Greeting() }
        lazySingleton { Greeting() }
        provider { Greeting() }
        singleton("singleton") { Greeting() }
        lazySingleton("lazySingleton") { Greeting() }
        provider("provider") { Greeting() }
    }

    val injector = Injector {
        importAll(androidModule, CommonInjector.mainModule)
    }.build()
}