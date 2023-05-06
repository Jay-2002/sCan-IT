package eu.udemytutorials.scan_it

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class Activity2 : AppCompatActivity() {

    private lateinit var imageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        imageView = findViewById(R.id.imgview1)

        val imageUriString = intent.getStringExtra("imageUri")

        // Set the image to the imageView
        if (imageUriString != null) {
            imageView.setImageURI(Uri.parse(imageUriString))
        } else {
            // If the image URI is null, show an error message
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }


        /*
        Basically when u try to pass bitmaps through intent,
        even after clicking or selecting the image, it passes a null value through intent

        HENCE WE USED THE TEMPORARY FILE METHOD
         */



        // Retrieve the image URI or bitmap from the intent
//        val imageUriString = intent?.getStringExtra("imageUri")
//        val imageBitmap : Bitmap? = intent?.getParcelableExtra<Bitmap>("imageBitmap")
//
//        // Set the image to the imageView
//        if (imageUriString != null) {
//            imageView.setImageURI(Uri.parse(imageUriString))
//        } else if (imageBitmap != null) {
//            imageView.setImageBitmap(imageBitmap)
//        }
//        else{
//            Toast.makeText(this,"No",Toast.LENGTH_SHORT).show()
//        }
        /////////////////////////////////////////////////////////////////
//        val imageBitmap = data?.extras?.get("data") as Bitmap
//        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(imageFile))
//        val imageView = findViewById<ImageView>(R.id.imgview1)
//        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
//        val imageUriString = intent.getStringExtra("imageUri")
//        if (imageBitmap != null) {
//            imageView.setImageBitmap(imageBitmap)
//        } else if (imageUriString != null) {
//            val imageUri = Uri.parse(imageUriString)
//            imageView.setImageURI(imageUri)
//        }

    }
}