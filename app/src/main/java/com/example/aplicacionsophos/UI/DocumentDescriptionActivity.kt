package com.example.aplicacionsophos.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.aplicacionsophos.R

class DocumentDescriptionActivity : AppCompatActivity() {
    lateinit var tipodocument: TextView
    lateinit var b64: TextView
    lateinit var imagedecode: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_description)
        tipodocument=findViewById(R.id.tipodocumentdescription)
        b64=findViewById<TextView>(R.id.tipodocumentb64)
        imagedecode=findViewById(R.id.imageViewDecode)
        val tipodocumento=intent.extras?.get("tipo") as String
        val photobase64=intent.extras?.get("adjunto") as String
       // b64.text= photobase64
        tipodocument.text=tipodocumento
        val decodedByte = Base64.decode(photobase64, Base64.DEFAULT)
        val decodedImage  = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        imagedecode.setImageBitmap(decodedImage);
    }
}