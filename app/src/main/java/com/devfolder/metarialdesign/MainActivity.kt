package com.devfolder.metarialdesign

import android.Manifest
import android.Manifest.permission.*
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Camera
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.devfolder.metarialdesign.core.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val phoneNumber="01680861295"
    private val RequestPhoneCall=1
    private val receipent="shajiddcc@gmail.com"
    private val subject="Test Subject"
    private val shareText="sharable text"
    private val PermissionCode=100
    private val ImageCaptureCode=101
    var image_uri: Uri? =null
    private var imageHolder=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageLoad()
        val district= listOf("Dhaka","Cumilla","Noakhali","Rangpur","Bogura","Zassore","Coxs Bazar","Syhlet")
        listview.adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,district)

        listview.setOnItemClickListener { _, _, position, _ ->
            if (position%2==0)
                showToast(" this is odd position")
            else
                showToast(" this is even position")
        }
        btn_cl.setOnClickListener {
           if (checkPermissionCall()){
               actionCall()
           }
           else
               reqPermissionCall()

          }
        btn_email.setOnClickListener {
           sendEmail()
        }
        btn_share.setOnClickListener {
          shareText()
        }
        btn_camera.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED
                ){
                    val permission= arrayOf(CAMERA,WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission,PermissionCode)
                }
                else openCamera()

            }
            else openCamera()
        }
        btn_gallery.setOnClickListener {
            imageHolder=image_uri.toString()
            val intent=Intent(Intent.ACTION_VIEW)
            intent.setClass(this,GalleryActivity::class.java)
            intent.putExtra("image",imageHolder)
            startActivity(intent)
        }

    }

    private fun openCamera() {
        val values=ContentValues()
        values.put(MediaStore.Images.Media.TITLE ,"New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION ,"From the Camera")
        image_uri=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri)
        startActivityForResult(cameraIntent,ImageCaptureCode)
    }

    private fun imageLoad() {
        Glide
            .with(this)
            .load("https://images.pexels.com/photos/417074/pexels-photo-417074.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
            .placeholder(R.drawable.ic_baseline_share_24)
            .error(R.drawable.ic_baseline_share_24)
            .fallback(R.drawable.ic_baseline_share_24)
            .into(iv_topImage)
    }
    private fun shareText(){
        val shareIntent=Intent()
        shareIntent.action=Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareText)
        startActivity(Intent.createChooser(shareIntent,"Share via"))
    }
    private fun actionCall(){
        val callIntent=Intent(Intent.ACTION_CALL)
        callIntent.data= Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }
    private fun reqPermissionCall(){
    return ActivityCompat.requestPermissions(this, arrayOf(CALL_PHONE),RequestPhoneCall)
}
    private fun checkPermissionCall(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext,CALL_PHONE)==PackageManager.PERMISSION_GRANTED
    }
    private fun sendEmail(){
        val emailIntent=Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL,arrayOf(receipent))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject)
        emailIntent.setType("message/rfc822")
        startActivity(Intent.createChooser(emailIntent, "Send Email using:"))
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==RequestPhoneCall)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                actionCall()
        when(requestCode){
           PermissionCode ->{
           if (grantResults.size>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED)
               else showToast("permission denied")
           }

        }

        }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode==Activity.RESULT_OK)
//        {
//            btn_gallery.setImageURI(image_uri)
//        }
//    }

}