package com.example.aplicacionsophos.UI.SendData

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.ViewModel.AplicationViewModel
import com.example.aplicacionsophos.data.model.Document
import com.example.aplicacionsophos.data.model.Office
import kotlinx.android.synthetic.main.fragment_send_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class SendDataFragment : Fragment() {
    private var officeList: List<Office> = emptyList<Office>()
     companion object {
        fun newInstance() = SendDataFragment()
    }
    private lateinit var viewModel: AplicationViewModel
    private var cityname = ArrayList<String>()
    lateinit var edtname: EditText
    lateinit var edtnumber: EditText
    lateinit var edtlastname: EditText
    lateinit var edtemail: EditText
    lateinit var edtcity: EditText
    lateinit var spinner_documenttype: Spinner
    lateinit var actualdate:String
    lateinit var bitmap: Bitmap
    private val CAMERA_PERMISION=1
    private val CAMERA_REQUEST_CODE=2
    private var document= Document("","","","","","","","","","")
    private var img64b=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_send_data, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AplicationViewModel::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getOficeList()
        }
        viewModel.oficeList.observe(viewLifecycleOwner, Observer {list-> officeList=list  })
        updatecity()
        init()
        val spinner_city=view?.findViewById<Spinner>(R.id.spinnercity2)
        spinner_city!!.adapter=ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,cityname)
        val buttonphoto= view?.findViewById<ImageButton>(R.id.Camerabutton2)
        buttonphoto?.setOnClickListener { requestpermissions()}
        val sendbutton= view?.findViewById<Button>(R.id.Sendfile2)
        sendbutton?.setOnClickListener {
            val id=edtnumber.text.toString()
            val name=edtname.text.toString()
            val lastname=edtlastname.text.toString()
            val city = edtcity.text.toString()
            val mail= edtemail.text.toString()
            val attached=attachedtype.text.toString()
              if (id=="" || name=="" || lastname=="" ||city=="" || mail==""  ) {
                showMessage("No deben haber campos vacios")
            } else if(img64b!=""){
                  document= Document("117",actualdate,"CC",id, name,lastname,city,mail,attached,img64b)
                  viewModel.postdocument(document)
              }
            else  {
                showMessage("No se ha adjuntado imagen")
            }

        }
        val Loadfile= view?.findViewById<Button>(R.id.Loadfile2)
            Loadfile?.setOnClickListener {
            pickmedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
    val pickmedia=registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            uri->
        if (uri!=null){
            bitmap= MediaStore.Images.Media.getBitmap(requireContext().contentResolver,uri)
            b64converter()
        }else{ }
    }
    private fun date(): String? {
        val timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM.dd.yyy"))
        return timestamp
    }
    private fun updatecity() {
        officeList.forEach {
                ofice->
            cityname.add(ofice.Ciudad)
        }
    }
    private fun init(){
        edtname= view?.findViewById(R.id.edtName2)!!
        edtnumber= view?.findViewById(R.id.edtDocumentnumber2)!!
        edtlastname= view?.findViewById(R.id.edtlastname2)!!
        edtemail= view?.findViewById(R.id.edtemail2)!!
        edtcity= view?.findViewById(R.id.edtcity2)!!
        actualdate=date().toString()
        spinner_documenttype= view?.findViewById<Spinner>(R.id.spinner_documenttype2)!!
        val optionsDocumenttipe= arrayOf("CC", "TI", "PA", "CE")
        spinner_documenttype.adapter=
            ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,optionsDocumenttipe)
    }
    private fun b64converter(){
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val valor= Base64.encodeToString(b, Base64.DEFAULT)
        showMessage(valor)
        img64b =valor
    }
    private fun requestpermissions(){
        when{
            ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED ->{
                takephoto()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)->{
                showMessage("el permiso fue rechazado ,habilitar en los ajustes")
            } else->{
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA),CAMERA_PERMISION)
        }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CAMERA_PERMISION->{
                if(grantResults.isNotEmpty() ){
                    takephoto()
                }
            } else ->{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    private fun takephoto() {
        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAMERA_REQUEST_CODE->{
                if(resultCode != Activity.RESULT_OK){
                    showMessage("no se tomo foto")
                }else{
                    bitmap=data?.extras?.get("data") as Bitmap
                    b64converter()
                }
            }
        }
    }
}