package com.example.aplicacionsophos.UI

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.aplicacionsophos.R

class Mytoolbar {
    fun show(activities: AppCompatActivity ,title:String,upButton:Boolean ) {
        activities.setSupportActionBar(activities.findViewById(R.id.toolbar))
        activities.supportActionBar?.title =title
        activities.supportActionBar?.setDisplayHomeAsUpEnabled(upButton)
    }
}