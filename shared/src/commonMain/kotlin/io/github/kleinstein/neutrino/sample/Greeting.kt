package io.github.kleinstein.neutrino.sample

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}