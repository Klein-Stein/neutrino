package io.github.kleinstein.neutrino.sample

import io.github.kleinstein.neutrino.DI
import io.github.kleinstein.neutrino.provider
import io.github.kleinstein.neutrino.resolve
import io.github.kleinstein.neutrino.singleton
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object CommonInjector {
    val mainModule = DI.module("main") {
        singleton { Greeting() }
        provider { GreetingContainer(resolve()) }
    }
}