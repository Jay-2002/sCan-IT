package eu.udemytutorials.scan_it

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
        val text = intent.getStringExtra("text")
        val dettext = findViewById<TextView>(R.id.dettext)
        dettext.text = text
    }
}