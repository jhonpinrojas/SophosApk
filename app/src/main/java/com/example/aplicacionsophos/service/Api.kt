package com.example.aplicacionsophos.service
import com.example.aplicacionsophos.data.model.Document
import com.example.aplicacionsophos.data.model.DocumentsResponse
import com.example.aplicacionsophos.data.model.User
import com.example.aplicacionsophos.data.model.officeResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {
    @GET (value="RS_Usuarios")
    fun postlogin(@Query(value = "idUsuario") idUsuario:String,@Query(value = "clave") clave:String):Call<User>
    @GET (value="RS_Oficinas")
    fun getofices():Call<officeResponse>
    @GET (value="RS_Documentos")
    fun getdocuments(@Query(value = "idRegistro") idRegistro:String,@Query(value = "correo") correo:String):
            Call<DocumentsResponse>
    @POST (value="RS_Documentos")
    fun postdocuments(@Body document: Document):
            Call<Document>

        companion object Factory{
        private const val BASE_URL = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/"
        fun create(): Api {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(Api::class.java)
        }
    }


    }
