package com.example.aplicacionsophos.UI.Main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.ViewModel.AplicationViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var viewModel: AplicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AplicationViewModel::class.java)
        val buttonSendocument = view?.findViewById<Button>(R.id.button_senddocument2)
        val buttonoffice = view?.findViewById<Button>(R.id.button_sendoffice2)
        val buttonwritedocument = view?.findViewById<Button>(R.id.button_writedocument2)
        buttonSendocument?.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_getDataFragment2) }
        buttonoffice?.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_mapFragment2)}
        buttonwritedocument?.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_sendDataFragment2)}

    }


}