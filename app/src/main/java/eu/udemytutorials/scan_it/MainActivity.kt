package eu.udemytutorials.scan_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        //THIS IS DONE SO THAT OUR LOGO REMAINS THERE FOR 3 SECONDS
        // BEFORE GOING TO OUR MAIN ACTIVITY
        Handler().postDelayed({
            startActivity(Intent(this, HomePageActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }, 3000)
    }
}