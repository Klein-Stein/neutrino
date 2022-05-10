## Welcome to Neutrino

[![version](https://img.shields.io/badge/version-2.0-brightgreen)](https://mvnrepository.com/artifact/io.github.klein-stein/neutrino/2.0)
[![license](https://img.shields.io/badge/license-MIT-brightgreen)](LICENSE.txt)

Simple dependency injector for Kotlin Mutiplatform Mobile. You can fully explore its code while drinking coffee.

## Install

Gradle (Groovy): 

```groovy
commonMain {
    dependencies {
        implementation 'io.github.klein-stein:neutrino:2.0'
    }
}
```

Gradle (Kotlin DSL):

```kotlin
commonMain {
    dependencies {
        implementation("io.github.klein-stein:neutrino:2.0")
    }
}
```

To use Neutrino in Android app directly just add it to dependencies as other libraries.


## Usage

Example of declaration:
```kotlin
import io.github.kleinstein.neutrino.*
import kotlin.native.concurrent.ThreadLocal

class Stub(val name: String)

@ThreadLocal
object CommonInjector {
    private val uncontrollableStub = Stub("weakStub")
   
    private val mainModule = DI.module("mainModule") {
        singleton { Stub("singleton1") }
       
        singleton("singleton2") { Stub("singleton2") }  // Use tags to inject two separate 
                                                        // instances of the same type
        provider("provider") { Stub("provider") }
       
        weakSingleton("weakSingleton") { uncontrollableStub }   // This object will be stored by the 
                                                                // weak reference
    }

    private val secondaryModule = DI.module("secondaryModule") {
        singleton("secondarySingleton") { Stub("secondarySingleton") }
       
        provider("secondaryProvider") { Stub("secondaryProvider") }
    }
    
    private val di = DI.global.apply {
        attachAll(mainModule, secondaryModule)  // Attach modules to the global DI container
//        attach(mainModule)
//        attach(secondaryModule)
    }
   
    // We can also create a local DI container instance
    val diLocal: DI = NeutrinoDI {
        attachAll(mainModule, secondaryModule)
    }
}
```

Example of injection for Android module or Common module:  

```kotlin
class SomeClass {
    private val di = DI.global()
   
    val stubSingleton: Stub = di.resolve()              // `singleton`
   
    val stubSingleton2: Stub = di.resolve("singleton2") // `singleton2`
   
    val lazyStubSingleton: Stub by di.resolveLazy()     // `singleton` with lazy initialization
                                                        // (all calls must be lazy or object will be 
                                                        // initialized on the first `resolve` call)
   
    val newStubInstance: Stub = di.resolve("provider")  // `provider`
   
    val newLazyStubInstance: Stub by di.resolveLazy("provider")     // `provider` with lazy 
                                                                    // initialization
   
    val weakSingleton: Weak<Stub> = di.resolve("weakSingleton")     // `weakStub`
}
```

Example of injection for iOS module:

```kotlin
class MainInjector {
   private val di = DI.global().apply {
       attachAll(/* modules */)
   }
   
   val singleton: Stub = di.resolve()  // `singleton`
   
   fun provider(): Stub = di.resolve("provider") // `provider`
   
   // And so on...
}
```

Then on Swift side:
```swift
class Injector {
    static let main = MainInjector()
    
    init() {}
}

struct ContentView: View {
    let singleton = Injector.main.singleton

	var body: some View {
        Text(singleton.name)
	}
}
```

Neutrino offers several fabrics to create objects:  

1. Singletons are objects that will live while Neutrino module or references on the singletons exist:

```kotlin
singleton { SomeObjectToBeSingleton() }
```

2. Providers, a new instance will be created on each `resolve()` call:

```kotlin
provider { SomeObjectToBeProvider() }
```

3. Weak singletons allow to inflate instances that may be unavailable in any time. Unlikely 
   the singletons, a real instance won't be held by Neutrino:
   
```kotlin
val someObjectToBeReferred = /* Initialization */
weakSingleton { someObjectToBeReferred }
```

## Roadmap

- [x] Singletons
- [x] Providers
- [x] Lazy resolving
- [x] Module support
- [x] Multiple injectors
- [x] Weak references
- [ ] Thread safety
- [ ] Swift-friendly

## Licensing
Project is licensed under the MIT license. See [LICENSE](https://github.com/Klein-Stein/neutrino/blob/master/LICENSE.txt) for the full license text.
