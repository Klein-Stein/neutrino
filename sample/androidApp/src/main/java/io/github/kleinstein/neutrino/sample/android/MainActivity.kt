package io.github.kleinstein.neutrino.sample.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.kleinstein.neutrino.sample.Greeting
import android.widget.TextView
import io.github.kleinstein.neutrino.DI
import io.github.kleinstein.neutrino.resolve

class MainActivity : AppCompatActivity() {
    private val di = DI.global()
    private val greeting: Greeting = di.resolve()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greeting.greeting()
    }
}
