package eu.udemytutorials.scan_it

import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission_group.CAMERA
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Camera
import android.hardware.Camera.open
import android.hardware.SensorPrivacyManager.Sensors.CAMERA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.open
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.nio.channels.AsynchronousFileChannel.open
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap

import android.app.Activity
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream
import java.sql.Types.NULL
import java.text.SimpleDateFormat
import java.util.*


class HomePageActivity : AppCompatActivity() {

    private lateinit var btnClick : Button
    private lateinit var btnupload : Button
    private lateinit var  adb : AlertDialog.Builder
    val CAMERA_REQUEST_CODE = 100
    val GALLERY_REQUEST_CODE = 200



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

//        imgview = findViewById(R.id.imgview1)
        btnClick = findViewById(R.id.btn_click)
        btnClick.setOnClickListener(){
            clickImage()
        }

        btnupload = findViewById(R.id.btn_upload)
        btnupload.setOnClickListener(){
            pickImage()
        }

    }

    private fun pickImage() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is granted, launch the gallery
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        } else {
            // Permission is not granted, request for permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_REQUEST_CODE
            )
        }
    }

    private fun clickImage() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED)
        {
            // Permission is granted, launch the camera
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            // Permission is not granted, request for permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, launch the camera
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                // Permission is not granted, show a message to the user
                Toast.makeText(
                    this,
                    "Camera permission is required to use the camera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, launch the gallery
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, GALLERY_REQUEST_CODE)
            } else {
                // Permission is not granted, show a message to the user
                Toast.makeText(
                    this,
                    "Gallery permission is required to access the gallery",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    if(resultCode == RESULT_OK){
                        val imageBitmap = data?.extras?.get("data") as Bitmap
                        if(imageBitmap != null){
                            val file = createTemporaryFile()
                            val outputStream = FileOutputStream(file)
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                            outputStream.flush()
                            outputStream.close()

                            // Pass the file URI to the next activity
                            val intent = Intent(this, Activity2::class.java)
                            intent.putExtra("imageUri", Uri.fromFile(file).toString())
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                        }
//                        val imageBitmap = data?.extras?.get("data") as Bitmap
//                        if(imageBitmap != null){
//                            val intent = Intent(this, Activity2::class.java)
//                            // Add the image bitmap as an extra to the intent
//                            intent.putExtra("image", imageBitmap)
//                            // Start the next activity
//                            startActivity(intent)
//                            // Set the image bitmap to an ImageView
//                        }else{
//                            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
//                        }
                    }
                }
                GALLERY_REQUEST_CODE -> {
                    val imageUri = data?.data
                    if(resultCode == RESULT_OK){
                        try {
                            val file = createTemporaryFile()
                            val inputStream = imageUri?.let { contentResolver.openInputStream(it) }
                            val outputStream = FileOutputStream(file)
                            inputStream?.copyTo(outputStream)
                            outputStream.close()
                            inputStream?.close()

                            val intent = Intent(this, Activity2::class.java)
                            intent.putExtra("imageUri", file.toURI().toString())
                            startActivity(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
//                        if (imageUri != null) {
//                            val intent = Intent(this, Activity2::class.java)
//                            intent.putExtra("image", imageUri.toString())
//                            startActivity(intent)
//                        } else {
//                            // If imageUri is null, display an error message or stay on the same page
//                            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
//                        }
                    }
                }
            }
        }
    }

    private fun createTemporaryFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        )
        return file
    }


}


