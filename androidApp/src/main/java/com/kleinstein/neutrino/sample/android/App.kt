package com.kleinstein.neutrino.sample.android

import android.app.Application
import com.kleinstein.neutrino.fabrics.Provider
import com.kleinstein.neutrino.fabrics.Singleton
import com.kleinstein.neutrino.sample.Greeting

class App: Application() {
    val greetingSingleton = Singleton {
        Greeting()
    }
    val greetingProvider = Provider {
        Greeting()
    }
}