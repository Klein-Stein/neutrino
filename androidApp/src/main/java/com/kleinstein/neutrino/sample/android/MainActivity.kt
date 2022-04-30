package com.kleinstein.neutrino.sample.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kleinstein.neutrino.sample.Greeting
import android.widget.TextView
import com.kleinstein.neutrino.DI
import com.kleinstein.neutrino.resolve

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
