import SwiftUI
import shared

struct ContentView: View {
    @StateObject private var viewModel = ViewModel()

	var body: some View {
        VStack {
            Text(viewModel.greeting)
        }
	}
}

class Injector {
    static let instance = MainInjector()
}

extension ContentView {
    class ViewModel: ObservableObject {
        @Published var greeting: String = Injector.instance.greeting.greeting()
        
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
