package com.devfolder.metarialdesign

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        val extras = intent.extras
        if (extras != null && extras.containsKey("image")) {
            val uri= Uri.parse(extras.getString("image"))
            iv_gallery.setImageURI(uri)
        }

    }
}