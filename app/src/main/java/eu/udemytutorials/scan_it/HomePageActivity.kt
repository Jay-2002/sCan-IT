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


class HomePageActivity : AppCompatActivity() {

    private lateinit var btnClick : Button
    private lateinit var  adb : AlertDialog.Builder
    private lateinit var imgview : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        imgview = findViewById(R.id.imgview1)
        btnClick = findViewById(R.id.btn_click)
        btnClick.setOnClickListener{
            selectImage()
        }

    }

    private fun selectImage() {
        val items = arrayOf<String>("Camera", "Gallery", "Cancel")
        adb = AlertDialog.Builder(this)
        adb.setTitle("Choose image from..")
            .setItems(items, DialogInterface.OnClickListener{
                        dialog, which->
                if(which==0){
                    val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
                        // Permission is not granted, request for permission

                    } else {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, 1)
                        // Permission is already granted, proceed with your camera related operations
                    }
                }
                else if(which==1){

                }
                else{
                    dialog.cancel()
                }

                })
        adb.show()
    }

//    private fun checkCameraPermission(): Boolean {
//        return true
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 1 && resultCode == RESULT_OK) {
            val photo = data?.extras!!["data"] as Bitmap?
            imgview.setImageBitmap(photo)
    }
}

}