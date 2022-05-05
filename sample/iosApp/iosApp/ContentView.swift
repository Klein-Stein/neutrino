import SwiftUI
import shared

struct ContentView: View {
    let greeting = MainInjector().greeting

	var body: some View {
        VStack {
            Text(greeting.greeting())
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
