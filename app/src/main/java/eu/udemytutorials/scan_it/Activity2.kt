package eu.udemytutorials.scan_it

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptions.DEFAULT_OPTIONS

class Activity2 : AppCompatActivity() {

    private lateinit var imageView : ImageView
    private lateinit var btnletsgo : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        imageView = findViewById(R.id.imgview1)
        btnletsgo= findViewById(R.id.btnletsgo)
        btnletsgo.setOnClickListener(){
            detectText()
        }
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

        It's possible that the image was not properly passed through the intent. When passing
        data through intents, it's important to ensure that the data type being passed is
        supported by the intent. For example, if you are passing a bitmap, you need to ensure
        that the receiving activity knows how to handle a bitmap. Another possible issue is that
        the image was not properly loaded or set in the receiving activity's ImageView. Make sure
        that the ImageView is properly initialized and that the image is being set to it using
        the appropriate method. Additionally, if you are passing a large image through the intent,
        it's possible that it could be exceeding the maximum size limit for data that can be passed
        through an intent. In such cases, it may be necessary to use alternative methods such as
        storing the image in a temporary file and passing the file URI through the intent,
        as we did earlier.
         */

        /* THE PROBLEM OF DIFFERENT IMAGE SIZE OF CLICKED & PICKED IMAGE
        The reason for the different size of the image in the ImageView could be due to the
        difference in the resolution of the image. When you click a picture from the camera, the
        resolution of the image is generally higher than the image picked from the gallery. So, when
        you set the image to the ImageView, it gets scaled according to the size of the ImageView,
        which might result in a different size of the image.
        To make the images appear the same size, you can scale the image before setting it to the
        ImageView. You can use a library like Glide or Picasso to load and scale the image according
        to your ImageView size. Alternatively, you can also use the Bitmap.createScaledBitmap()
        method to create a scaled bitmap of the image and then set it to the ImageView.
        *  */



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
//    fun extractTextFromImage(image: Bitmap): String {
//        val tessBaseApi = TessBaseAPI()
//        tessBaseApi.init(applicationContext.getExternalFilesDir(null)?.absolutePath, "eng")
//        tessBaseApi.setImage(image)
//        val extractedText = tessBaseApi.utF8Text
//        tessBaseApi.end()
//        return extractedText
//    }
private fun detectText() {
    val imageUriString = intent.getStringExtra("imageUri")
    if (imageUriString != null) {
        val image = InputImage.fromFilePath(this, Uri.parse(imageUriString))
        val recognizer = TextRecognition.getClient(DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Get the recognized text
                val text = visionText.text
                val intent = Intent(this, Activity3::class.java)
                intent.putExtra("text", text)
                startActivity(intent)

                //Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                // Handle any errors that occurred during text recognition
                Toast.makeText(this, "Text recognition failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}


}