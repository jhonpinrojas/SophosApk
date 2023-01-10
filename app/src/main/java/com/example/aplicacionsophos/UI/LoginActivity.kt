package com.example.aplicacionsophos.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.data.model.User
import com.example.aplicacionsophos.service.Api
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private val apiservice: Api by lazy {
        Api.create()
    }
    private var idregistro=""
    private var mail=""
    private var nameuser=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonlogin=findViewById<Button>(R.id.login)
        buttonlogin.setOnClickListener {
            performLogin()
        }
    }



    private fun performLogin(){
        val email= findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        val call=apiservice.postlogin(email,password)
        call.enqueue(object :Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) =
                if(response.isSuccessful) {
                    val loginresponse = response.body()

                    if (loginresponse == null) {
                        Toast.makeText(applicationContext, "error de servidor", Toast.LENGTH_SHORT).show()
                    }  else {
                        val id= loginresponse.acceso.toString()
                        if(loginresponse.acceso.equals(true) ){
                            Toast.makeText(applicationContext, "bienvenido", Toast.LENGTH_SHORT).show()
                            idregistro=loginresponse.id.toString()
                            nameuser=loginresponse.nombre
                            mail=email
                            gotomenu()
                            }else {
                            Toast.makeText(applicationContext,"credenciales erroneas",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                        Toast.makeText(applicationContext,"credenciales erroneas",Toast.LENGTH_SHORT).show()
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
      //  i.putExtra("mail", mail)
        startActivity(i)

    }



}