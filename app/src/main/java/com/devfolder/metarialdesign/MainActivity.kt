package com.devfolder.metarialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.devfolder.metarialdesign.core.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageLoad()
        val district= listOf("Dhaka","Cumilla","Noakhali","Rangpur","Bogura","Zassore","Coxs Bazar","Syhlet")
        listview.adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,district)
        listview.setOnItemClickListener { parent, view, position, id ->
            if (position%2==0)
                showToast(" this is odd position")
            else
                showToast(" this is even position")
        }


    }

    fun imageLoad() {
        Glide
            .with(this)
            .load("https://images.pexels.com/photos/417074/pexels-photo-417074.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
            .placeholder(R.drawable.ic_baseline_share_24)
            .error(R.drawable.ic_baseline_share_24)
            .fallback(R.drawable.ic_baseline_share_24)
            .into(iv_topImage)
    }
}