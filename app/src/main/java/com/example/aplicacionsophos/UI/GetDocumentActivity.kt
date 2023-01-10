package com.example.aplicacionsophos.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.data.model.Document
import com.example.aplicacionsophos.data.model.Office
import kotlinx.android.synthetic.main.watch_document_activity.*


class GetDocumentActivity : AppCompatActivity() {
    //private val bundle= intent.extras
    private lateinit var viewModel: AplicationViewModel
    private var documentList: List<Document> = emptyList<Document>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.watch_document_activity)
        viewModel= ViewModelProvider(this).get(AplicationViewModel::class.java)
        initUI()
    }


    private fun initUI(){
        watchdocumentreciclerview.layoutManager =LinearLayoutManager(this)

       // val correo= bundle?.getString("mail").toString()
        //val id=bundle?.getString("idregistro").toString()
        viewModel.getDocumentList("117","jhonpinrojas@gmail.com")

        viewModel.documentList.observe(this, Observer { list ->
            (watchdocumentreciclerview.adapter as GetDocumentAdapter).setData(list)
            documentList=list
        })
        watchdocumentreciclerview.adapter=GetDocumentAdapter{
            val adjunto = documentList[it].Adjunto
            val tipo = documentList[it].TipoAdjunto
            val i=Intent(this,DocumentDescriptionActivity::class.java)
            i.putExtra("adjunto", adjunto)
            i.putExtra("tipo", tipo)
            Toast.makeText(this,"envio a revision",Toast.LENGTH_SHORT).show()
            startActivity(i)

        }
    }
}