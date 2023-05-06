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
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.sql.Types.NULL


class HomePageActivity : AppCompatActivity() {

    private lateinit var btnClick : Button
    private lateinit var btnupload : Button
    private lateinit var  adb : AlertDialog.Builder
//    private lateinit var imgview : ImageView
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
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    if(imageBitmap != null){
                        val intent = Intent(this, Activity2::class.java)
                        // Add the image bitmap as an extra to the intent
                        intent.putExtra("image", imageBitmap)
                        // Start the next activity
                        startActivity(intent)
                        // Set the image bitmap to an ImageView
                    }else{
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                    }
//                    imgview.setImageBitmap(imageBitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        val intent = Intent(this, Activity2::class.java)
                        intent.putExtra("image", imageUri?.toString())
                        startActivity(intent)
                    } else {
                        // If imageUri is null, display an error message or stay on the same page
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                    }
                    // Set the image uri to an ImageView

                }
            }
        }
    }

//    private fun selectImage() {
//        val items = arrayOf<String>("Camera", "Gallery", "Cancel")
//        adb = AlertDialog.Builder(this)
//        adb.setTitle("Choose image from..")
//            .setItems(items, DialogInterface.OnClickListener{
//                        dialog, which->
//                if(which==0){
//                    val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                    if (permission != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(this,
//                            arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
//                    }
//                    else {
//                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                        startActivityForResult(intent, CAMERA_REQUEST_CODE)
////
//                    }
//                }
//                else if(which==1){
//                    val galleryPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                    if (galleryPermission == PackageManager.PERMISSION_GRANTED) {
//                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                        startActivityForResult(intent, GALLERY_REQUEST_CODE)
//                    } else {
//                        ActivityCompat.requestPermissions(this,
//                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_REQUEST_CODE)
//                    }
//                }
//                else{
//                    dialog.cancel()
//                }
//
//                })
//        adb.show()
//    }

//    private fun checkCameraPermission(): Boolean {
//        return true
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//    if (requestCode == 1 && resultCode == RESULT_OK) {
//            val photo = data?.extras!!["data"] as Bitmap?
//            imgview.setImageBitmap(photo)
//    }
//override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//    when (requestCode) {
//        CAMERA_REQUEST_CODE -> {
//            if (resultCode == Activity.RESULT_OK) {
//                val imageBitmap = data?.extras?.get("data") as Bitmap
//                imgview.setImageBitmap(imageBitmap)
//                val intent = Intent(this, Activity2::class.java)
//                // Do something with the image bitmap
//            }
//        }
//        GALLERY_REQUEST_CODE -> {
//            if (resultCode == Activity.RESULT_OK) {
//                val imageUri = data?.data
//                imgview.setImageURI(imageUri)
//                val intent = Intent(this, Activity2::class.java)
//                // Do something with the image uri
//            }
//        }
//    }
//    if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//        val imageBitmap = data?.extras?.get("data") as Bitmap
//        imgview.setImageBitmap(imageBitmap)
//        // Do something with the image bitmap
//    }

}


