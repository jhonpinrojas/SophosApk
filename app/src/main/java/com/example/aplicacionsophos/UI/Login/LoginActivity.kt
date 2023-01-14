package com.example.aplicacionsophos.UI.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.UI.Main.Main
import com.example.aplicacionsophos.ViewModel.AplicationViewModel
import com.example.aplicacionsophos.data.model.User
import com.example.aplicacionsophos.service.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: AplicationViewModel

     private val apiservice: Api by lazy {
        Api.create()
    }

    private var idregistro=""
    private var mail=""
    private var nameuser=""
    private lateinit var  infolist: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(AplicationViewModel::class.java)
        val buttonlogin=findViewById<Button>(R.id.login)
        buttonlogin.setOnClickListener {
               performLogin()
               /* viewModel.getuserlogin(email,password)
                viewModel.loginuser.observe(this, Observer {
                        Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT).show()
                })*/
                }

    }



    private fun performLogin(){
        val email= findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        val call=apiservice.postlogin(email,password)
        call.enqueue(object :Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful) {
                    val loginresponse = response.body()

                    if (loginresponse == null) {
                        Toast.makeText(applicationContext, "error de servidor", Toast.LENGTH_SHORT).show()
                    }  else {
                        val id= loginresponse.acceso.toString()
                        if(loginresponse.acceso.equals(true) ){
                            Toast.makeText(applicationContext, "bienvenido", Toast.LENGTH_SHORT).show()
                            gotomenu()
                        }else {
                            Toast.makeText(applicationContext,"usuario o contraseña incorrecto",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(applicationContext,"credenciales erroneas",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext,"Error de conexion",Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun gotomenu(){
        val i= Intent(this, Main::class.java)
      // i.putExtra("name", nameuser)
       // i.putExtra("idregistro", nameuser)
        i.putExtra("mail", mail)
        startActivity(i)

    }



}