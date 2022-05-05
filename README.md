neutrino
=========

Simple dependency injector for Kotlin Multiplatform Mobile

## Install

Gradle (Groovy): 

```groovy
commonMain {
    dependencies {
        implementation 'io.github.klein-stein:neutrino:1.0'
    }
}
```

Gradle (Kotlin DSL):

```kotlin
commonMain {
    dependencies {
        implementation("io.github.klein-stein:neutrino:1.0")
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
    private val mainModule = DI.module("main") {
        singleton { Stub("mainSingleton") }
        singleton("singleton2") { Stub("mainSingleton2") } // Use tags to inject two separate 
                                                           // instances of the same type
        lazySingleton("mainLazySingleton") { Stub("mainLazySingleton") }
        provider("mainProvider") { Stub("mainProvider") }
        lazyProvider("mainLazyProvider") { Stub("mainLazyProvider") }
    }

    private val secondaryModule = DI.module("secondary") {
        singleton("secondarySingleton") { Stub("secondarySingleton") }
        lazySingleton("secondaryLazySingleton") { Stub("secondaryLazySingleton") }
        provider("secondaryProvider") { Stub("secondaryProvider") }
        lazyProvider("secondaryLazyProvider") { Stub("secondaryLazyProvider") }
    }
    
    private val injector = DI.injector("default") {
        attachAll(mainModule, secondaryModule)
//        attach(mainModule)
//        attach(secondaryModule)
    }
   
    val di = DI.global.attach(injector)  // Attach the injector to the global DI container
    // We can also create a local DI container instance
    val diLocal: DI = NeutrinoDI {
        attach(injector)
    }
}
```

Example of injection for Android module or Common module:  

```kotlin
class SomeClass {
    private val di = DI.global()
    val stubSingleton: Stub = di.resolve()  // mainSingleton
    val stubSingleton2: Stub = di.resolve("singleton2") // mainSingleton2
    val lazyStubSingleton: Stub by di.resolveLazy("mainLazySingleton") // mainLazySingleton
    val newStubInstance: Stub = di.resolve("mainProvider") // mainProvider
    val newLazyStubInstance: Stub by di.resolve("mainLazyProvider") // mainLazyProvider
}
```

Example of injection for iOS module:

```kotlin
class MainInjector {
   private val di = DI.global()
   val stubSingleton: Stub = di.resolve()  // mainSingleton
   val stubSingleton2: Stub = di.resolve("singleton2") // mainSingleton2
   val lazyStubSingleton: Stub by di.resolveLazy("mainLazySingleton") // mainLazySingleton
   fun newStubInstance() = di.resolve("mainProvider") // mainProvider
   fun newLazyStubInstance() = di.resolve("mainLazyProvider") // mainLazyProvider
}
```

Then on Swift side:
```swift
class Injector {
    static let main = MainInjector()
    
    init() {}
}

struct ContentView: View {
    let stubSingleton = Injector.main.stubSingleton

	var body: some View {
        Text(stubSingleton.name)
	}
}
```

Neutrino offers several fabrics to create objects:  

1. Singletons, objects that will live while Neutrino module or references on the singletons exist:

```kotlin
singleton { SomeObjectToBeSingleton() }
```

2. Lazy singletons, objects that will live while Neutrino module or references on the singletons 
   exist. Unlikely the singletons, the lazy singletons will be initialized at the first access 
   moment:
   
```kotlin
lazySingleton { SomeObjectToBeSingleton() }
```

3. Providers, a new instance will be created on each `resolve()` call:

```kotlin
provider { SomeObjectToBeProvider() }
```

4. Lazy providers, a new instance will be created on each `resolve()` call. Unlikely the providers, 
   a real instance in the memory will be initialized at the first access moment:
   
```kotlin
provider { SomeObjectToBeProvider() }
```

## Roadmap

- [x] Singletons
- [x] Providers
- [x] Lazy singletons
- [x] Lazy providers
- [x] Module support
- [x] Multiple injectors
- [ ] Weak references
- [ ] Thread safety
- [ ] Swift-friendly

## Pull requests

Any changes in pull requests must be offshoots from the devel branch.

## Licensing
Project is licensed under the MIT license. See [LICENSE](LICENSE.txt) for the full license text.
