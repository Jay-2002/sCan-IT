package eu.udemytutorials.scan_it

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Activity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        val imageView = findViewById<ImageView>(R.id.imgview1)
        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
        val imageUriString = intent.getStringExtra("imageUri")
        if (imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap)
        } else if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            imageView.setImageURI(imageUri)
        }
    
    }
}