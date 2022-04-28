import SwiftUI
import shared
import neutrino

struct ContentView: View {
    let injector = CommonInjector.injector

	var body: some View {
        VStack {
            Text("Singleton: " + injector.greetingSingleton.build()!.greeting())
            Text("Provider: " + injector.greetingProvider.build()!.greeting())
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

class CommonInjector {
    static let injector = CommonInjector()
    
    init() {}
    
    let greetingSingleton = Singleton {
        Greeting()
    }
    
    let greetingProvider = Provider {
        Greeting()
    }
}
