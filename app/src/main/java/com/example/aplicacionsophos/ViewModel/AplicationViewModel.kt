package com.example.aplicacionsophos.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplicacionsophos.data.model.*
import com.example.aplicacionsophos.service.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class AplicationViewModel: ViewModel() {
private  val BASE_URL = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val service: Api = retrofit.create(Api::class.java)
//private val repository = Aplicationrepository(service)
//private val logininfo = MutableLiveData<User>()

val documentList = MutableLiveData<List<Document>>()
val oficeList = MutableLiveData<List<Office>>()
//private val logininfo = LiveData<User>()
val loginuser = MutableLiveData<String>()
val iduser = MutableLiveData<Int>()


fun getDocumentList(id:String,correo:String){
    val call = service.getdocuments(id,correo)
    call.enqueue(object : Callback<DocumentsResponse>{
        override fun onResponse(call: Call<DocumentsResponse>, response: Response<DocumentsResponse>) {
            response.body()?.Items?.let { list ->
                documentList.postValue(list)
            }
        }
        override fun onFailure(call: Call<DocumentsResponse>, t: Throwable) {
            call.cancel()
        }

    })
}

    fun getOficeList() {
        val call = service.getofices()
        call.enqueue(object : Callback<officeResponse>{
            override fun onResponse(call: Call<officeResponse>, response: Response<officeResponse>) {
                response.body()?.Items?.let { list ->
                    oficeList.postValue(list)
                }
            }
            override fun onFailure(call: Call<officeResponse>, t: Throwable) {
                call.cancel()
            }

        })
}



    suspend fun getuserlogin(correo: String, password:String){/*
        CoroutineScope(Dispatchers.IO).launch {
        var userLogin=service.postlogin(correo,password)
            val response = service.postlogin(correo, password)
            if(response.isSuccessful) {
                response.body()?.let { list -> logininfo.postValue(list) }

                if (userLogin == null) {
                    response.errorBody().toString()
                }else{
                    if(response.body()?.acceso?. == true){
                        /*    Toast.makeText(applicationContext, "bienvenido", Toast.LENGTH_SHORT).show()
                        gotomenu()*/
                        response.body()?.nombre?.let { name -> loginuser.postValue(name) }
                        response.body()?.id?.let { id -> iduser.postValue(id) }
                        }else {
                        response.acceso
                    }
                }
            }
            else{
                response.errorBody().toString()
            }

        }*/
    }


    fun postdocument(document: Document){
        val call=service.postdocuments(document)
        call.enqueue(object : Callback<Document> {
            override fun onResponse(call: Call<Document>, response: Response<Document>) {
                if(response.isSuccessful) {
                    val postresponse = response.body()
                    if (postresponse == null) {
                        call.cancel()
                    } else{
                        call.execute()
                    }
                }
            }


            override fun onFailure(call: Call<Document>, t: Throwable) {
                call.cancel()
            }

        })
    }
}

