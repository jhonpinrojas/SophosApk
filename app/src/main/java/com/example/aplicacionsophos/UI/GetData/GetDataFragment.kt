package com.example.aplicacionsophos.UI.GetData

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.ViewModel.AplicationViewModel
import com.example.aplicacionsophos.data.model.Document
import kotlinx.android.synthetic.main.fragment_get_data.*

class GetDataFragment : Fragment() {
    private var documentList: List<Document> = emptyList<Document>()
    companion object {
        fun newInstance() = GetDataFragment()
    }

    private lateinit var viewModel: AplicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_get_data, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AplicationViewModel::class.java)
        initUI()
    }

    private fun initUI(){
        watchdocumentreciclerview2.layoutManager = LinearLayoutManager(requireContext())

        // val correo= bundle?.getString("mail").toString()
        //val id=bundle?.getString("idregistro").toString()
        viewModel.getDocumentList("117","jhonpinrojas@gmail.com")
        viewModel.documentList.observe(viewLifecycleOwner, Observer { list ->
            (watchdocumentreciclerview2.adapter as GetDocumentAdapter).setData(list)
            documentList=list
        })
        watchdocumentreciclerview2.adapter= GetDocumentAdapter{
            val adjunto = documentList[it].Adjunto
            val tipo = documentList[it].TipoAdjunto
            val i= Intent(requireContext(), DocumentDescriptionActivity::class.java)
            i.putExtra("adjunto", adjunto)
            i.putExtra("tipo", tipo)
            startActivity(i)

        }

    }

}