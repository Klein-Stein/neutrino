import SwiftUI
import shared
import neutrino

struct ContentView: View {

	var body: some View {
        VStack {
            Text("Works!")
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
