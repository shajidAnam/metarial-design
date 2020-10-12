package com.devfolder.metarialdesign.core

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    fun showToast(text:String){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }
}