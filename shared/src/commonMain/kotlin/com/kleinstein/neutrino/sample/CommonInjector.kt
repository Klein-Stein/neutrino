package com.kleinstein.neutrino.sample

import com.kleinstein.neutrino.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object CommonInjector {
    val mainModule = DI.module("main") {
        singleton { Greeting() }
        provider { GreetingContainer(resolve()) }
    }
}