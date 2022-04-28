package com.kleinstein.neutrino.sample

import com.kleinstein.neutrino.fabrics.Provider
import com.kleinstein.neutrino.fabrics.Singleton

object CommonInjector {
    val greetingSingleton = Singleton {
        Greeting()
    }
    val greetingProvider = Provider {
        Greeting()
    }
}