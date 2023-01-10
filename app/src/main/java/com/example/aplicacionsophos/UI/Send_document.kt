package com.example.aplicacionsophos.UI

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.data.model.Document
import com.example.aplicacionsophos.data.model.Office
import com.example.aplicacionsophos.service.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class Send_document : AppCompatActivity() {
    private lateinit var viewModel: AplicationViewModel
    private var officeList: List<Office> = emptyList<Office>()
    private var cityname = ArrayList<String>()
    lateinit var edtname:EditText
    lateinit var edtnumber:EditText
    lateinit var edtlastname:EditText
    lateinit var edtemail:EditText
    lateinit var edtcity:EditText
    lateinit var spinner_documenttype:Spinner
    lateinit var actualdate:String
    lateinit var Loadfile:Button
    lateinit var buttonphoto:ImageButton
    lateinit var sendbutton:Button
    lateinit var bitmap:Bitmap
    private val CAMERA_PERMISION=1
    private val CAMERA_REQUEST_CODE=2
    private var document= Document("","","","","","","","","","")
    private var img64b=""


    val pickmedia=registerForActivityResult(PickVisualMedia()){
        uri->
        if (uri!=null){
            bitmap=MediaStore.Images.Media.getBitmap(contentResolver,uri)
            b64converter()
        }else{ }
    }

    /*   private val apiservice: Api by lazy {
        Api.create()
    }*/


        @SuppressLint("CutPasteId", "SuspiciousIndentation")
        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_senddocument)
            viewModel= ViewModelProvider(this).get(AplicationViewModel::class.java)
            viewModel.getOficeList()
            viewModel.oficeList.observe(this, Observer {list-> officeList=list  })
            updatecity()
            init()
            val spinner_city=findViewById<Spinner>(R.id.spinnercity)
            spinner_city.adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,cityname)

            buttonphoto=findViewById(R.id.Camerabutton)
            buttonphoto.setOnClickListener { requestpermissions()}
            sendbutton=findViewById(R.id.Sendfile)
            sendbutton.setOnClickListener {
                if(img64b!=""){
                document= Document("117",actualdate,"CC",edtnumber.text.toString(),
                                    edtname.text.toString(),edtlastname.text.toString(),edtcity.text.toString(),
                                    edtemail.text.toString(),"Incapacidad",img64b)
                    viewModel.postdocument(document)
                    //performdocument()
                } else {
                    showMessage("No se ha adjuntado imagen")
                }

            }
            Loadfile=findViewById(R.id.Loadfile)
            Loadfile.setOnClickListener {
                pickmedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }
        }


    private fun requestpermissions(){
        when{
            ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED ->{
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

    fun showMessage(message: String) {
            Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
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
    private fun date(): String? {
        val timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM.dd.yyy"))
        return timestamp
    }
  /*  private fun performdocument(){

        val call=apiservice.postdocuments(document)
        call.enqueue(object : Callback<Document> {
            override fun onResponse(call: Call<Document>, response: Response<Document>) {
                if(response.isSuccessful) {
                val postresponse = response.body()
                if (postresponse == null) {
                    Toast.makeText(applicationContext,"no ok", Toast.LENGTH_SHORT).show()
                  } else{
                    Toast.makeText(applicationContext,"si ok", Toast.LENGTH_SHORT).show()
                  }
                }
            }


            override fun onFailure(call: Call<Document>, t: Throwable) {
                Toast.makeText(applicationContext,"Error de conexion", Toast.LENGTH_SHORT).show()
            }

        })
    }*/

    private fun updatecity() {
        officeList.forEach {
                ofice->
            cityname.add(ofice.Ciudad)
        }
    }
    private fun init(){
        edtname=findViewById(R.id.edtName)
        edtnumber=findViewById(R.id.edtDocumentnumber)
        edtlastname=findViewById(R.id.edtlastname)
        edtemail=findViewById(R.id.edtemail)
        edtcity=findViewById(R.id.edtcity)
        actualdate=date().toString()
        spinner_documenttype=findViewById<Spinner>(R.id.spinner_documenttype)
        val optionsDocumenttipe= arrayOf("CC", "TI", "PA", "CE")
        spinner_documenttype.adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,optionsDocumenttipe)
    }
    private fun b64converter(){
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val valor= Base64.encodeToString(b, Base64.DEFAULT)
        showMessage(valor)
        img64b =valor
    }
}