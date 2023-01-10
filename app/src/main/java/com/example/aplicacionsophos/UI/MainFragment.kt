package com.example.aplicacionsophos.UI

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import com.example.aplicacionsophos.R
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    var nigthvariable = 0
    var languagevarible = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val buttonSendocument = view?.findViewById<Button>(R.id.button_senddocument2)
        val buttonoffice = view?.findViewById<Button>(R.id.button_sendoffice2)
        val buttonwritedocument = view?.findViewById<Button>(R.id.button_writedocument2)
        buttonSendocument?.setOnClickListener { getdocument() }
        buttonoffice?.setOnClickListener { gotomap() }
        buttonwritedocument?.setOnClickListener { gotosenddocument() }

    }

    private fun getdocument() {
        //val z= Intent(this, GetDocumentActivity::class.java)
        //startActivity(z)
        val getdatafrag = GetDataFragment()
        val transaccion: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaccion.replace(R.id.container, getdatafrag)
        transaccion.commitNow()
    }

    private fun gotosenddocument() {
        //val i= Intent(this, Send_document::class.java)
        //val idregistro=bundle?.getString("idregistro")
        // val mail=bundle?.getString("mail")
        //startActivity(i)
        //i.putExtra("idregistro", idregistro)
        //i.putExtra("mail", mail)
        val senddatafrag = SendDataFragment()
        val transaccion: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaccion.replace(R.id.container, senddatafrag)
        transaccion.commitNow()
    }

    private fun gotomap() {
        // viewModel.getOficeList()
        //  val lista = viewModel.oficeList.value
        //val i= Intent(this, OficeMapsActivity::class.java)
        // i.putExtra("miLista", lista)
        // startActivity(i)
        val mapdatafrag = MapFragment()
        val transaccion: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaccion.replace(R.id.container, mapdatafrag)
        transaccion.commitNow()
    }

}